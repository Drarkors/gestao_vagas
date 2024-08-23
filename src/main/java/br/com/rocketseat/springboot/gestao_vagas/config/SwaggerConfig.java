package br.com.rocketseat.springboot.gestao_vagas.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI()
        .info(
            new Info()
                .title("Gestão de Vagas")
                .description("API responsável pela gestão de vagas")
                .version("1.0.0")
        ).schemaRequirement("jwt_auth", createSecurityScheme());
  }

  private SecurityScheme createSecurityScheme() {
    return new SecurityScheme()
        .name("jwt_auth")
        .type(SecurityScheme.Type.HTTP)
        .scheme("bearer")
        .bearerFormat("JWT")
        .in(SecurityScheme.In.HEADER);
  }

}
