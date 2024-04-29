package com.vitai.events.configs;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER,
        type = SecuritySchemeType.HTTP
)
public class SwaggerConfig {

        @Bean
        public OpenAPI customAPI() {
                return new OpenAPI()
                        .info(new Info()
                                .title("Events API")
                                .description("OpenAPI documentation for Events API + Spring Security")
                                .version("1.0.0")
                        );
        }

}
