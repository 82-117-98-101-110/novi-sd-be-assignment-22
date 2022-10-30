package com.ravel.backend.shared;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
	name = "bearerAuth",
	type = SecuritySchemeType.HTTP,
	bearerFormat = "JWT",
	scheme = "bearer"
)
public class OpenApi30Config {

	@Value("${info.app.version}")
	private String version;

	@Value("${info.app.environment}")
	private String environment;

	@Bean
	public OpenAPI springShopOpenAPI() {
		return new OpenAPI()
				.addServersItem(
						new Server().url("https://dev.ravel.systems").description("Dev base URL")
				)
				.addServersItem(
						new Server().url("https://live.ravel.systems").description("Prod base URL")
				)
				//			.addServersItem(
				//				new Server().url("https://test.ravel.systems").description("Testing")
				//			)
				.addServersItem(
						new Server().url("http://localhost:8080").description("Local base URL")
				)
			.info(
				new Info()
					.title("Ravel API")
					.description(
						" **Current environment** = " +
						" " +
						environment +
						"\n " +
						"\n " +
						"Ravel REST API is the latest supported version and in this documentation we expose all the details on how to access these resources and what you can expect as return values. " +
						"This documentation assumes a basic knowledge of how REST APIs work. Visit [https://www.restapitutorial.com/](https://www.restapitutorial.com/)  if you need an introduction to this topic. " +
						"Data is will be send and received as JSON, except for resources where files are uploaded or download.  Other api's are also available here: [https://dev.ravel.systems/environments/swagger-ui.html](https://dev.ravel.systems/environments/swagger-ui.html)"
					)
					.version(version)
					.license(new License().name("Apache 2.0").url("http://springdoc.org"))
			)
			.externalDocs(
				new ExternalDocumentation()
					.description("Ravel documentation")
					.url("https://www.notion.so/thenewbase")
			);
	}

	//	@Bean
	//	public GroupedOpenApi authApi() {
	//		return GroupedOpenApi
	//			.builder()
	//			.group("Authentication")
	//			.pathsToMatch("/**/auth/**")
	//			.build();
	//	}
	//
	//	@Bean
	//	public GroupedOpenApi adminApi() {
	//		return GroupedOpenApi
	//			.builder()
	//			.group("Admin")
	//			.pathsToMatch("/**/admin/**")
	//			.build();
	//	}

//	@Bean
//	public GroupedOpenApi everything() {
//		return GroupedOpenApi.builder().group("Everything").pathsToMatch("/**").build();
//	}
}
