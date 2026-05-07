package duoc.fs3.ms_weather.cron;

import duoc.fs3.ms_weather.model.WeatherData;
import duoc.fs3.ms_weather.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio de cron job para la actualización automática de datos del clima.
 * 
 * Esta clase se ejecuta periódicamente según la configuración en application.properties
 * para obtener y almacenar los datos del clima desde la API externa.
 * 
 * @author Duoc UC Fullstack III
 * @version 1.0
 * @since 2026
 */
@Service
@Slf4j
public class WeatherCronService {

    private final WeatherApiService weatherApiService;
    private final WeatherService weatherService;

    @Value("${weather.api.retry.attempts:3}")
    private int maxRetryAttempts;

    /**
     * Constructor que inyecta los servicios necesarios.
     */
    public WeatherCronService(WeatherApiService weatherApiService, WeatherService weatherService) {
        this.weatherApiService = weatherApiService;
        this.weatherService = weatherService;
    }

    /**
     * Método ejecutado por el cron job para actualizar los datos del clima.
     * Se ejecuta según la expresión configurada en application.properties.
     */
    @Scheduled(cron = "${weather.cron.expression}")
    public void updateWeatherData() {
        log.info("Iniciando actualización automática de datos del clima");
        
        int attempt = 0;
        boolean success = false;
        
        while (attempt < maxRetryAttempts && !success) {
            attempt++;
            try {
                log.info("Intento {} de actualización de datos del clima", attempt);
                
                List<WeatherData> weatherDataList = fetchAndProcessWeatherData();
                
                if (!weatherDataList.isEmpty()) {
                    weatherService.saveAllWeatherData(weatherDataList);
                    log.info("Se guardaron {} registros de datos del clima", weatherDataList.size());
                    success = true;
                } else {
                    log.warn("No se obtuvieron datos del clima en el intento {}", attempt);
                }
                
            } catch (Exception e) {
                log.error("Error en el intento {} de actualización: {}", attempt, e.getMessage());
                if (attempt >= maxRetryAttempts) {
                    log.error("Se agotaron los intentos de actualización del clima");
                }
            }
        }
        
        if (success) {
            log.info("Actualización de datos del clima completada exitosamente");
        } else {
            log.error("Falló la actualización de datos del clima después de {} intentos", maxRetryAttempts);
        }
    }

    /**
     * Obtiene y procesa los datos del clima desde la API externa.
     * 
     * @return Lista de WeatherData procesados
     */
    private List<WeatherData> fetchAndProcessWeatherData() {
        var response = weatherApiService.fetchWeatherData();
        
        List<WeatherData> weatherDataList = new ArrayList<>();
        
        if (response != null && response.getData() != null) {
            for (var cityData : response.getData()) {
                WeatherData weatherData = WeatherData.builder()
                        .cityCode(cityData.getCode())
                        .cityName(cityData.getCity())
                        .temperature(parseTemperature(cityData.getTemperature()))
                        .weatherCondition(cityData.getCondition())
                        .humidity(cityData.getHumidity())
                        .updatedAt(cityData.getUpdated_at())
                        .build();
                
                weatherDataList.add(weatherData);
            }
        }
        
        return weatherDataList;
    }

    /**
     * Convierte el valor de temperatura a BigDecimal.
     * 
     * @param temperature Valor de temperatura desde la API
     * @return BigDecimal con el valor de temperatura
     */
    private BigDecimal parseTemperature(Object temperature) {
        if (temperature == null) {
            return null;
        }
        
        if (temperature instanceof Number) {
            return BigDecimal.valueOf(((Number) temperature).doubleValue());
        }
        
        try {
            return new BigDecimal(temperature.toString());
        } catch (NumberFormatException e) {
            log.warn("No se pudo parsear la temperatura: {}", temperature);
            return null;
        }
    }
}
