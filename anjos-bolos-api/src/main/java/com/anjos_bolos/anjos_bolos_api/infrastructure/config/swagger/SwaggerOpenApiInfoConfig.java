package com.anjos_bolos.anjos_bolos_api.infrastructure.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerOpenApiInfoConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Anjos-Bolos API 🍰")
                        .description("Documentação Oficial da API de Registro de Vendas da Anjos Bolos Confeitaria 🍰")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("INSPIRA-Consulting")
                                .email("inspira@sptech.school")));
    }
}