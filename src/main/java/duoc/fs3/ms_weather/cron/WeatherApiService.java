package duoc.fs3.ms_weather.cron;

import duoc.fs3.ms_weather.model.WeatherApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

/**
 * Servicio para consumir la API externa de clima.
 * 
 * Esta clase se encarga de realizar las llamadas HTTP a la API
 * https://api.boostr.cl/weather.json para obtener los datos del clima.
 * 
 * @author Duoc UC Fullstack III
 * @version 1.0
 * @since 2026
 */
@Service
@Slf4j
public class WeatherApiService {

    private final RestTemplate restTemplate;

    @Value("${weather.api.url}")
    private String apiUrl;

    @Value("${weather.api.timeout.connect:5000}")
    private int connectTimeout;

    @Value("${weather.api.timeout.read:10000}")
    private int readTimeout;

    /**
     * Constructor que inyecta RestTemplate configurado.
     * 
     * @param restTemplate RestTemplate para llamadas HTTP
     */
    public WeatherApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Obtiene los datos del clima desde la API externa.
     * 
     * @return WeatherApiResponse con los datos obtenidos
     * @throws RuntimeException Si hay un error al consumir la API
     */
    public WeatherApiResponse fetchWeatherData() {
        log.info("Obteniendo datos del clima desde la API: {}", apiUrl);
        
        try {
            WeatherApiResponse response = restTemplate.getForObject(apiUrl, WeatherApiResponse.class);
            
            if (response != null && "success".equals(response.getStatus())) {
                log.info("Datos del clima obtenidos exitosamente. Total de ciudades: {}", 
                        response.getData() != null ? response.getData().size() : 0);
                return response;
            } else {
                log.error("La API devolvió un estado inválido: {}", response != null ? response.getStatus() : "null");
                throw new RuntimeException("Respuesta inválida de la API del clima");
            }
            
        } catch (Exception e) {
            log.error("Error al consumir la API del clima: {}", e.getMessage(), e);
            throw new RuntimeException("No se pudieron obtener los datos del clima: " + e.getMessage(), e);
        }
    }

    /**
     * Verifica si la API está disponible.
     * 
     * @return true si la API responde correctamente, false en caso contrario
     */
    public boolean isApiAvailable() {
        try {
            log.debug("Verificando disponibilidad de la API: {}", apiUrl);
            WeatherApiResponse response = restTemplate.getForObject(apiUrl, WeatherApiResponse.class);
            return response != null && "success".equals(response.getStatus());
        } catch (Exception e) {
            log.warn("La API del clima no está disponible: {}", e.getMessage());
            return false;
        }
    }
}
