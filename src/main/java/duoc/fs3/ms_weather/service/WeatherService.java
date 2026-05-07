package duoc.fs3.ms_weather.service;

import duoc.fs3.ms_weather.dto.response.WeatherResponse;
import duoc.fs3.ms_weather.model.WeatherData;
import duoc.fs3.ms_weather.repository.WeatherRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Servicio de lógica de negocio para la gestión de datos del clima.
 * 
 * Esta clase proporciona métodos para consultar y procesar los datos
 * del clima almacenados en la base de datos.
 * 
 * @author Duoc UC Fullstack III
 * @version 1.0
 * @since 2026
 */
@Service
@Slf4j
public class WeatherService {

    private final WeatherRepository weatherRepository;

    /**
     * Constructor que inyecta el repositorio de datos del clima.
     * 
     * @param weatherRepository Repositorio para operaciones de base de datos
     */
    public WeatherService(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    /**
     * Obtiene los datos del clima por nombre de ciudad.
     * 
     * @param cityName Nombre de la ciudad a buscar
     * @return WeatherResponse con los datos encontrados o mensaje de no encontrado
     */
    public WeatherResponse getWeatherByCityName(String cityName) {
        log.info("Buscando datos del clima para la ciudad: {}", cityName);
        
        Optional<WeatherData> weatherData = weatherRepository.findLatestByCityName(cityName);
        
        if (weatherData.isPresent()) {
            WeatherData data = weatherData.get();
            return WeatherResponse.builder()
                    .id(data.getId())
                    .cityCode(data.getCityCode())
                    .cityName(data.getCityName())
                    .temperature(data.getTemperature())
                    .weatherCondition(data.getWeatherCondition())
                    .humidity(data.getHumidity())
                    .updatedAt(data.getUpdatedAt())
                    .createdAt(data.getCreatedAt())
                    .message("Datos del clima encontrados exitosamente")
                    .build();
        } else {
            return WeatherResponse.builder()
                    .message("No se encontraron datos del clima para la ciudad: " + cityName)
                    .build();
        }
    }

    /**
     * Obtiene los datos del clima por código de ciudad.
     * 
     * @param cityCode Código de la ciudad a buscar
     * @return WeatherResponse con los datos encontrados o mensaje de no encontrado
     */
    public WeatherResponse getWeatherByCityCode(String cityCode) {
        log.info("Buscando datos del clima para el código de ciudad: {}", cityCode);
        
        Optional<WeatherData> weatherData = weatherRepository.findLatestByCityCode(cityCode);
        
        if (weatherData.isPresent()) {
            WeatherData data = weatherData.get();
            return WeatherResponse.builder()
                    .id(data.getId())
                    .cityCode(data.getCityCode())
                    .cityName(data.getCityName())
                    .temperature(data.getTemperature())
                    .weatherCondition(data.getWeatherCondition())
                    .humidity(data.getHumidity())
                    .updatedAt(data.getUpdatedAt())
                    .createdAt(data.getCreatedAt())
                    .message("Datos del clima encontrados exitosamente")
                    .build();
        } else {
            return WeatherResponse.builder()
                    .message("No se encontraron datos del clima para el código: " + cityCode)
                    .build();
        }
    }

    /**
     * Obtiene todos los datos más recientes del clima.
     * 
     * @return Lista de WeatherResponse con los datos más recientes de cada ciudad
     */
    public List<WeatherResponse> getAllLatestWeather() {
        log.info("Obteniendo todos los datos más recientes del clima");
        
        List<WeatherData> weatherDataList = weatherRepository.findAllLatest();
        
        return weatherDataList.stream()
                .map(this::convertToWeatherResponse)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene todos los datos del clima ordenados por fecha descendente.
     * 
     * @return Lista de WeatherResponse con todos los datos
     */
    public List<WeatherResponse> getAllWeather() {
        log.info("Obteniendo todos los datos del clima");
        
        List<WeatherData> weatherDataList = weatherRepository.findAllOrderByCreatedAtDesc();
        
        return weatherDataList.stream()
                .map(this::convertToWeatherResponse)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene el historial de datos del clima para una ciudad en un rango de fechas.
     * 
     * @param cityName Nombre de la ciudad
     * @param startDate Fecha de inicio (formato yyyy-MM-dd)
     * @param endDate Fecha de fin (formato yyyy-MM-dd)
     * @return Lista de WeatherResponse con el historial
     */
    public List<WeatherResponse> getWeatherHistory(String cityName, String startDate, String endDate) {
        log.info("Obteniendo historial del clima para {} entre {} y {}", cityName, startDate, endDate);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime start = LocalDateTime.parse(startDate + " 00:00:00", formatter);
        LocalDateTime end = LocalDateTime.parse(endDate + " 23:59:59", formatter);
        
        List<WeatherData> weatherDataList = weatherRepository.findByCityNameAndDateRange(cityName, start, end);
        
        return weatherDataList.stream()
                .map(this::convertToWeatherResponse)
                .collect(Collectors.toList());
    }

    /**
     * Convierte una entidad WeatherData a WeatherResponse.
     * 
     * @param weatherData Entidad a convertir
     * @return WeatherResponse con los datos convertidos
     */
    private WeatherResponse convertToWeatherResponse(WeatherData weatherData) {
        return WeatherResponse.builder()
                .id(weatherData.getId())
                .cityCode(weatherData.getCityCode())
                .cityName(weatherData.getCityName())
                .temperature(weatherData.getTemperature())
                .weatherCondition(weatherData.getWeatherCondition())
                .humidity(weatherData.getHumidity())
                .updatedAt(weatherData.getUpdatedAt())
                .createdAt(weatherData.getCreatedAt())
                .message("Datos del clima")
                .build();
    }

    /**
     * Guarda datos del clima en la base de datos.
     * 
     * @param weatherData Datos del clima a guardar
     * @return WeatherData guardado
     */
    public WeatherData saveWeatherData(WeatherData weatherData) {
        log.info("Guardando datos del clima para la ciudad: {}", weatherData.getCityName());
        return weatherRepository.save(weatherData);
    }

    /**
     * Guarda una lista de datos del clima en la base de datos.
     * 
     * @param weatherDataList Lista de datos del clima a guardar
     * @return Lista de WeatherData guardados
     */
    public List<WeatherData> saveAllWeatherData(List<WeatherData> weatherDataList) {
        log.info("Guardando {} registros de datos del clima", weatherDataList.size());
        return weatherRepository.saveAll(weatherDataList);
    }

    /**
     * Limpia registros antiguos de la base de datos.
     * 
     * @param daysToKeep Días a mantener (se eliminan los más antiguos)
     * @return Número de registros eliminados
     */
    public int cleanOldRecords(int daysToKeep) {
        log.info("Limpiando registros más antiguos que {} días", daysToKeep);
        
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(daysToKeep);
        return weatherRepository.deleteOldRecords(cutoffDate);
    }
}
