package com.example.jpa.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class Swagger2Config {
	@Bean
	public GroupedOpenApi publicApi() {
		return GroupedOpenApi.builder()
			.group("JPA API")
			.pathsToMatch("/api/**")
			.build();
	}

	@Bean
	public OpenAPI springShopOpenAPI() {
		return new OpenAPI()
			.info(new Info().title("JPA API")
				.description("실전! 스프링부트와 JPA 2 강의 예제 API 명세.")
				.version("v0.0.1"));
	}
}
