package com.anjos_bolos.anjos_bolos_api.infrastructure.config.swagger;

import org.springdoc.core.properties.SwaggerUiConfigParameters;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springdoc.core.properties.SwaggerUiOAuthProperties;
import org.springdoc.core.providers.ObjectMapperProvider;
import org.springdoc.webmvc.ui.SwaggerIndexTransformer;
import org.springdoc.webmvc.ui.SwaggerWelcomeCommon;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public SwaggerIndexTransformer swaggerIndexTransformer(
            SwaggerUiConfigProperties swaggerUiConfig,
            SwaggerUiOAuthProperties swaggerUiOAuthProperties,
            ObjectMapperProvider objectMapperProvider,
            SwaggerWelcomeCommon swaggerWelcomeCommon,
            SwaggerUiConfigParameters swaggerUiConfigParameters) {

        return new SwaggerCustomCssInjector(
                swaggerUiConfig,
                swaggerUiOAuthProperties,
                swaggerWelcomeCommon,
                swaggerUiConfigParameters,
                objectMapperProvider
        );
    }
}