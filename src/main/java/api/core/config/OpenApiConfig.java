package api.core.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Socila Media Api",
                description = "Socila Media HH", version = "1.0.0",
                contact = @Contact(
                        name = "Islam Azizov",
                        email = "Azizov.islam.t@gmail.com"
                )
        )
)
public class OpenApiConfig {
}
