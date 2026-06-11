package duoc.fs3.ms_weather.controller;

import duoc.fs3.ms_weather.dto.response.WeatherResponse;
import duoc.fs3.ms_weather.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de datos del clima.
 * 
 * Esta clase proporciona los endpoints para que el frontend pueda consultar
 * los datos del clima almacenados en la base de datos.
 * 
 * @author Duoc UC Fullstack III
 * @version 1.0
 * @since 2026
 */
@RestController
@RequestMapping("/api/weather")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200", "http://localhost:8081"})
@Tag(name = "Clima", description = "API para la consulta de datos del clima")
@Slf4j
public class WeatherController {

    private final WeatherService weatherService;

    /**
     * Constructor que inyecta el servicio de clima.
     * 
     * @param weatherService Servicio de lógica de negocio del clima
     */
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    /**
     * Obtiene los datos del clima por nombre de ciudad.
     * 
     * @param cityName Nombre de la ciudad a buscar
     * @return WeatherResponse con los datos encontrados
     */
    @GetMapping("/city/{cityName}")
    @Operation(summary = "Obtener clima por ciudad", description = "Obtiene los datos del clima para una ciudad específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Datos del clima encontrados"),
        @ApiResponse(responseCode = "404", description = "No se encontraron datos para la ciudad")
    })
    public ResponseEntity<WeatherResponse> getWeatherByCityName(
            @Parameter(description = "Nombre de la ciudad", example = "Santiago")
            @PathVariable String cityName) {
        
        log.info("Petición GET /api/weather/city/{}", cityName);
        
        WeatherResponse response = weatherService.getWeatherByCityName(cityName);
        
        if (response.getId() != null || response.getCityCode() != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Obtiene los datos del clima por código de ciudad.
     * 
     * @param cityCode Código de la ciudad a buscar
     * @return WeatherResponse con los datos encontrados
     */
    @GetMapping("/code/{cityCode}")
    @Operation(summary = "Obtener clima por código", description = "Obtiene los datos del clima para un código de ciudad específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Datos del clima encontrados"),
        @ApiResponse(responseCode = "404", description = "No se encontraron datos para el código")
    })
    public ResponseEntity<WeatherResponse> getWeatherByCityCode(
            @Parameter(description = "Código de la ciudad", example = "SCEL")
            @PathVariable String cityCode) {
        
        log.info("Petición GET /api/weather/code/{}", cityCode);
        
        WeatherResponse response = weatherService.getWeatherByCityCode(cityCode);
        
        if (response.getId() != null || response.getCityCode() != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Obtiene todos los datos más recientes del clima.
     * 
     * @return Lista de WeatherResponse con los datos más recientes de cada ciudad
     */
    @GetMapping("/all/latest")
    @Operation(summary = "Obtener todos los climas más recientes", description = "Obtiene los datos más recientes del clima para todas las ciudades")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de datos del clima obtenida")
    })
    public ResponseEntity<List<WeatherResponse>> getAllLatestWeather() {
        log.info("Petición GET /api/weather/all/latest");
        
        List<WeatherResponse> weatherList = weatherService.getAllLatestWeather();
        return ResponseEntity.ok(weatherList);
    }

    /**
     * Obtiene todos los datos del clima ordenados por fecha descendente.
     * 
     * @return Lista de WeatherResponse con todos los datos
     */
    @GetMapping("/all")
    @Operation(summary = "Obtener todos los climas", description = "Obtiene todos los datos del clima ordenados por fecha descendente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de datos del clima obtenida")
    })
    public ResponseEntity<List<WeatherResponse>> getAllWeather() {
        log.info("Petición GET /api/weather/all");
        
        List<WeatherResponse> weatherList = weatherService.getAllWeather();
        return ResponseEntity.ok(weatherList);
    }

    /**
     * Endpoint de health check para verificar que el servicio está funcionando.
     * 
     * @return Mensaje de estado del servicio
     */
    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Verifica que el servicio está funcionando correctamente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Servicio funcionando correctamente")
    })
    public ResponseEntity<String> healthCheck() {
        log.info("Petición GET /api/weather/health");
        return ResponseEntity.ok("ms-weather service is running");
    }
}
