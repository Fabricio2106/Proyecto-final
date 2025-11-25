package com.techsolution.gestion_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        // registrar esquema basic auth para Swagger UI
        SecurityScheme basicAuth = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("basic");
        return new OpenAPI()
                .info(new Info()
                        .title("TechSolution API")
                        .version("1.0")
                        .description("Documentación de la API")
                )
                .schemaRequirement("basicAuth", basicAuth)
                .addSecurityItem(new SecurityRequirement().addList("basicAuth"));
    }

}
