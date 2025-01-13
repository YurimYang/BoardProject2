package com.example.board_project.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(apiInfo())
                .components(new Components());
    }

    private Info apiInfo(){
        return new Info()
                .version("v1.0.0")
                .title("Board Project API")
                .description("Onboarding Board Project Swagger 입니다");
    }
}
