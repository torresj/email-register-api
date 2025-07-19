package com.torresj.email_register_api.configs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class SwaggerConfig {

    @Value("${info.app.version}")
    private final String version;

    @Bean
    public OpenAPI springOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("basicScheme",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("basic")))
                .addSecurityItem(new SecurityRequirement().addList("basicScheme"))
                .info(
                        new Info()
                                .title("Email Register API")
                                .description("Email Register API")
                                .version(version)
                                .license(
                                        new License()
                                                .name("GNU General Public License V3.0")
                                                .url("https://www.gnu.org/licenses/gpl-3.0.html")));
    }
}