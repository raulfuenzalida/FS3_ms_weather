package duoc.fs3.ms_weather.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuración de OpenAPI/Swagger para la documentación de la API.
 * 
 * Esta clase configura la documentación automática de los endpoints REST
 * utilizando SpringDoc OpenAPI 3.0.
 * 
 * @author Duoc UC Fullstack III
 * @version 1.0
 * @since 2026
 */
@Configuration
public class OpenApiConfig {

    @Value("${server.port:8081}")
    private String serverPort;

    /**
     * Configura el bean de OpenAPI con la información del microservicio.
     * 
     * @return Configuración de OpenAPI personalizada
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Microservicio ms-weather API")
                        .description("API para la gestión y consulta de datos del clima de Chile")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Duoc UC Fullstack III")
                                .email("contacto@duoc.cl"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:" + serverPort)
                                .description("Servidor de desarrollo"),
                        new Server()
                                .url("https://api.weather.duoc.cl")
                                .description("Servidor de producción")
                ));
    }
}
