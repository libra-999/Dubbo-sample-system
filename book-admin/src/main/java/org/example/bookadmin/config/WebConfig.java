package org.example.bookadmin.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebConfig {

    private final UserDetailsService userDetailsService;
    private final AuthEntryPoint authEntryPoint;

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        return userDetailsService;
    }

    @Bean
    public AuthenticationManager authManager(UserDetailsService service, PasswordEncoder encoder) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(service);
        daoAuthenticationProvider.setPasswordEncoder(encoder);
        return new ProviderManager(daoAuthenticationProvider);
    }

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
        authoritiesConverter.setAuthorityPrefix("");
        authoritiesConverter.setAuthoritiesClaimName("scope");

        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
        return converter;
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        return new InMemoryUserDetailsManager(User.withUsername("好玩").password("{noop}".concat("好吧")).authorities(
            "VIEW_ADMIN", "LIST_ADMIN", "ADD_ADMIN", "UPDATE_ADMIN",
            "DELETE_ADMIN", "VIEW_USER", "LIST_USER", "ADD_USER",
            "UPDATE_USER", "DELETE_USER", "VIEW_SUPERADMIN", "LIST_SUPERADMIN",
            "ADD_SUPERADMIN", "UPDATE_SUPERADMIN", "DELETE_SUPERADMIN"
        ).roles("ADMIN", "SUPERADMIN").build());
    }


    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Bean
    public SecurityFilterChain securityWebFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
            .exceptionHandling(auth -> auth.authenticationEntryPoint(authEntryPoint))
            .authorizeHttpRequests(auth ->
                auth
                    .requestMatchers("/admin/v1.0.0/api/**").permitAll()
                    .requestMatchers("/front/v1.0.0/api/**").authenticated()
                    .requestMatchers("/auth/v1.0.0/api/**",
                        "/documents", "/swagger-ui.html",
                        "/swagger-ui/**", "/v3/api-docs/**",
                        "/webjars/**").permitAll()
                    .anyRequest().denyAll()
            ).userDetailsService(userDetailsService)
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

