package org.example.gateway.filter;


import lombok.RequiredArgsConstructor;
import org.example.gateway.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.example.gateway.config.SecurityConfig.writeJsonResponse;

@RequiredArgsConstructor
public class JwtAuthFilter implements WebFilter {

    private final JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String path = exchange.getRequest().getPath().toString();

        if (shouldSkipAuth(path)) {
            return chain.filter(exchange);
        }

        String token = extractToken(exchange.getRequest());
        if (token == null || !jwtUtil.validateJwtToken(token)) {
            return writeJsonResponse(exchange.getResponse(), HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        String paths = exchange.getRequest().getURI().getPath();
        if (paths.startsWith("/actuator/**")) {
            return chain.filter(exchange);
        }


        String username = jwtUtil.getUserNameFromJwtToken(token);
        List<String> roles = jwtUtil.extractRoles(token);
        Authentication auth = new UsernamePasswordAuthenticationToken(
            username,null,roles.stream().map(SimpleGrantedAuthority::new).toList()
        );
        ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
            .header("X-User-ID", jwtUtil.getUserNameFromJwtToken(token))
            .header("X-Authentication", "true")
            .build();

        ServerWebExchange modifiedExchange = exchange.mutate().request(modifiedRequest).build();
        return chain.filter(modifiedExchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth));
    }


    private boolean shouldSkipAuth(String path) {
        return path.startsWith("/v1.0.0/api/auth") ||
            path.contains("/swagger-ui.html") ||
            path.contains("/swagger-ui") ||
            path.contains("/v3/api-docs") ||
            path.contains("/documents");
    }

    private String extractToken(ServerHttpRequest request) {
        List<String> headers = request.getHeaders().get("Authorization");
        if (headers == null || headers.isEmpty()) return null;
        String header = headers.get(0);
        return header.startsWith("Bearer ") ? header.substring(7) : null;
    }


}
