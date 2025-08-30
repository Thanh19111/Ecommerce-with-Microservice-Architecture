package com.thanhpham.User.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("${server.port:8080}")
    private String port;

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("User Service API")
                        .description("API for managing user profile")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("thanhpham")
                                .email("thanhpham@example.com")
                                .url("https://thanhpham.com"))
                )
                .servers(List.of(new Server().url("http://localhost:" + port)));
    }

    @Bean
    public GroupedOpenApi userAPI() {
        return GroupedOpenApi.builder()
                .group("user")
                .pathsToMatch("/users/**")
                .build();
    }
}
