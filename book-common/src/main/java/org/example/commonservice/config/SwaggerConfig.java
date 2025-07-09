package org.example.commonservice.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class SwaggerConfig {

    @Bean
    public OpenAPI licenseAPI() {

        Contact contact = new Contact()
            .name("libra")
            .email("libra123@gmail.com")
            .url("http://libraBBH.com");
        License mitLicense = new License()
            .name("BookReview Project")
            .url("http://book-shop.com");
        Info info = new Info()
            .title("Book API")
            .version("1.0")
            .contact(contact)
            .description("This API exposes endpoints to manage system for client.")
            .termsOfService("http://libraBBH.com/term")
            .license(mitLicense);

        return new OpenAPI().info(info)
            .components(new Components()
                .addSecuritySchemes("bearerAuth",
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                )
            );
    }
}
