/*
*  @(#)OpenAPI30Configuration.java
*
*  Copyright (c) J-Tech Solucoes em Informatica.
*  All Rights Reserved.
*
*  This software is the confidential and proprietary information of J-Tech.
*  ("Confidential Information"). You shall not disclose such Confidential
*  Information and shall use it only in accordance with the terms of the
*  license agreement you entered into with J-Tech.
*
*/
package br.com.jtech.tasklist.config.infra.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.OpenAPIV3Parser;
import io.swagger.v3.parser.core.models.SwaggerParseResult;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * OpenAPI 3.0 Configuration for Tasklist API
 * Documentation is loaded from openapi.yaml file
 *
 * @author angelo.vicente
 */
@Configuration
public class OpenAPI30Configuration {
    
    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("tasklist-api")
                .pathsToMatch("/api/v1/**")
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        try {
            ClassPathResource resource = new ClassPathResource("openapi.yaml");
            if (!resource.exists()) {
                throw new RuntimeException("openapi.yaml file not found in classpath");
            }
            
            InputStream inputStream = resource.getInputStream();
            String yamlContent = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            inputStream.close();
            
            OpenAPIV3Parser parser = new OpenAPIV3Parser();
            SwaggerParseResult parseResult = parser.readContents(yamlContent, null, null);
            
            if (parseResult.getMessages() != null && !parseResult.getMessages().isEmpty()) {
                System.err.println("OpenAPI YAML parsing warnings: " + parseResult.getMessages());
            }
            
            OpenAPI openAPI = parseResult.getOpenAPI();
            if (openAPI == null) {
                throw new RuntimeException("Failed to parse openapi.yaml: " + parseResult.getMessages());
            }
            
            return openAPI;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load openapi.yaml", e);
        }
    }

}