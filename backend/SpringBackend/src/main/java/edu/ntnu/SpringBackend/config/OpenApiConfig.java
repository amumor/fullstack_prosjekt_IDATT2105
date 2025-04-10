package edu.ntnu.SpringBackend.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

/**
 * OpenApi configuration for the application.
 * <p>
 * This class is responsible for configuring the OpenAPI documentation
 * for the application, including API information and security scheme.
 * </p>
 *
 * @author Amund Mørk
 * @version 1.0
 * @since 1.0
 */
@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(),
                description = "OpenApi documentation for find.no",
                title = "OpenAPI Documentation - find.no",
                version = "1.0"
        ),
        servers = {
                @Server(
                        description = "Local env",
                        url = "http://localhost:8080"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT Authorization header using the Bearer scheme",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
@Configuration
public class OpenApiConfig {
}