package duoc.fs3.ms_weather.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuración de RestTemplate para consumo de APIs externas.
 * 
 * Esta clase configura el bean RestTemplate con las configuraciones
 * necesarias para las llamadas HTTP a la API del clima.
 * 
 * @author Duoc UC Fullstack III
 * @version 1.0
 * @since 2026
 */
@Configuration
public class RestTemplateConfig {

    /**
     * Crea y configura el bean RestTemplate.
     * 
     * @return RestTemplate configurado para uso en la aplicación
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
