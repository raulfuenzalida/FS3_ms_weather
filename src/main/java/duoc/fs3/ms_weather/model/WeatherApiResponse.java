package duoc.fs3.ms_weather.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO que representa la respuesta de la API externa de clima.
 * 
 * Esta clase mapea la estructura JSON devuelta por la API
 * https://api.boostr.cl/weather.json
 * 
 * @author Duoc UC Fullstack III
 * @version 1.0
 * @since 2026
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherApiResponse {

    /**
     * Estado de la respuesta de la API.
     */
    private String status;

    /**
     * Lista de datos del clima para todas las ciudades.
     */
    private List<WeatherCityData> data;

    /**
     * Clase interna que representa los datos de una ciudad específica.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WeatherCityData {

        /**
         * Código de la ciudad (ej: SCEL, SCIE).
         */
        private String code;

        /**
         * Nombre de la ciudad.
         */
        private String city;

        /**
         * Hora de la última actualización.
         */
        private String updated_at;

        /**
         * Temperatura actual.
         */
        private Object temperature;

        /**
         * Condición climática.
         */
        private String condition;

        /**
         * Porcentaje de humedad.
         */
        private Integer humidity;
    }
}
