package duoc.fs3.ms_weather.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración web para CORS y otros aspectos del MVC.
 * 
 * Esta clase configura la política CORS para permitir el acceso
 * desde los orígenes especificados al microservicio.
 * 
 * @author Duoc UC Fullstack III
 * @version 1.0
 * @since 2026
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Configura la política CORS para la aplicación.
     * 
     * @param registry Registro de configuración CORS
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:3000", "http://localhost:4200", "http://localhost:8081")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
        
        // Configuración para Swagger/OpenAPI
        registry.addMapping("/swagger-ui/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "HEAD", "OPTIONS")
                .allowedHeaders("*");
        
        registry.addMapping("/v3/api-docs/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "HEAD", "OPTIONS")
                .allowedHeaders("*");
    }
}
