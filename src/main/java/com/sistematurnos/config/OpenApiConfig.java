package com.sistematurnos.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.*;
import io.swagger.v3.oas.models.security.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("UNLa Turnos - API REST")
                        .description("API para la gestión de turnos, usuarios, servicios y más")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Grupo 22 - OO2")
                                .email("grupo22@unla.edu.ar")
                        )
                        .license(new License().name("Apache 2.0").url("http://springdoc.org"))
                )
                .components(new Components()
                        .addSecuritySchemes("basicScheme",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("basic"))
                )
                .addSecurityItem(new SecurityRequirement().addList("basicScheme"));
    }
}
