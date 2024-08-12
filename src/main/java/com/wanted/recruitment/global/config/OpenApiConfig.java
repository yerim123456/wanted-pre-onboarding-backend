package com.wanted.recruitment.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;

@Configuration
public class OpenApiConfig {
	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
			.info(new io.swagger.v3.oas.models.info.Info()
				.title("wanted-pre-onboarding-backend")
				.description("Spring boot를 이용한 REST API 채용 관리 프로젝트")
				.version("0.0.1")
				.contact(new io.swagger.v3.oas.models.info.Contact()
					.name("이예림")
					.url("https://github.com/yerim123456")
					.email("yearim1226@naver.com")));
	}
}