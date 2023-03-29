package br.com.cronos.redesocial.config;

import javax.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;

@OpenAPIDefinition(
    info = @Info(
        title="Api Rede Social",
        version = "1.0.1",
        contact = @Contact(
            name = "Rodrigo Ferreira",
            url = "https://www.linkedin.com/in/rodrigo-ferreira-de-souza-desenvolvedor-java-spring-angular/",
            email = "roferreira122@gmail.com"),
        license = @License(
            name = "Apache 2.0",
            url = "https://www.apache.org/licenses/LICENSE-2.0.html"))
)
public class RedeSocialOpenApiApplication extends Application {
}