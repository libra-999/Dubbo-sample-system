package org.example.gateway.filter;


import lombok.RequiredArgsConstructor;
import org.example.gateway.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

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
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
            .header("X-User-ID", jwtUtil.getUserNameFromJwtToken(token))
            .header("X-User-Roles", String.join(",", jwtUtil.extractRoles(token)))
            .build();

        return chain.filter(exchange.mutate().request(modifiedRequest).build());
    }

    private boolean shouldSkipAuth(String path) {
        return path.startsWith("/v1.0.0/api/auth") ||
            path.contains("/swagger-ui.html") ||
            path.contains("/swagger-ui") ||
            path.contains("/v3/api-docs") ||
            path.contains("/documents") ||
            path.contains("/webjars");
    }

    private String extractToken(ServerHttpRequest request) {
        List<String> headers = request.getHeaders().get("Authorization");
        if (headers == null || headers.isEmpty()) return null;
        String header = headers.get(0);
        return header.startsWith("Bearer ") ? header.substring(7) : null;
    }


}
