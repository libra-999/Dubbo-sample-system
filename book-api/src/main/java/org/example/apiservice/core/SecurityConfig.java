package org.example.apiservice.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityWebFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(auth ->
                auth
                    .requestMatchers("/admin/v1.0.0/api/**").hasAnyRole("ADMIN", "MANAGER", "MONITOR", "STAFF")
                    .requestMatchers("/front/v1.0.0/api/**").authenticated()
                    .requestMatchers("/auth/v1.0.0/api/**",
                        "/documents", "/swagger-ui.html",
                        "/swagger-ui/**", "/v3/api-docs/**",
                        "/webjars/**").permitAll()
                    .anyRequest().denyAll()

            ).sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).build();
    }
}
