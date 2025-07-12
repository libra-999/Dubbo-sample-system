package org.example.gateway.config;

import lombok.RequiredArgsConstructor;
import org.example.gateway.filter.JwtAuthFilter;
import org.example.gateway.util.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .authorizeExchange(auth -> auth
                .pathMatchers("/admin/v1.0.0/api/**").hasAnyRole("ADMIN", "MANAGER", "MONITOR", "STAFF")
                .pathMatchers("/front/v1.0.0/api/**").authenticated()
                .pathMatchers("/auth/v1.0.0/api/**",
                    "/documents", "/swagger-ui.html","/actuator/**",
                    "/swagger-ui/**", "/v3/api-docs/**", "/webjars/**").permitAll()
                .anyExchange().authenticated()
            )
            .addFilterAt(new JwtAuthFilter(jwtUtil), SecurityWebFiltersOrder.AUTHENTICATION)
            .exceptionHandling(exceptions -> exceptions
                .authenticationEntryPoint((exchange, ex) ->
                    writeJsonResponse(exchange.getResponse(),HttpStatus.UNAUTHORIZED,"Unauthorized"))
                .accessDeniedHandler((exchange, ex) ->
                   writeJsonResponse(exchange.getResponse(), HttpStatus.FORBIDDEN,"Access Denied"))
            )
            .build();
    }

    public static Mono<Void> writeJsonResponse(ServerHttpResponse response, HttpStatus status, String message) {
        response.setStatusCode(status);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        String body = "{\"message\": \"" + message + "\"}";
        DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
