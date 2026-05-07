package duoc.fs3.ms_weather.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO de respuesta para los datos del clima.
 * 
 * Esta clase representa la respuesta enviada al frontend
 * con la información del clima de una ciudad específica.
 * 
 * @author Duoc UC Fullstack III
 * @version 1.0
 * @since 2026
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherResponse {

    /**
     * Identificador único del registro.
     */
    private Long id;

    /**
     * Código de la ciudad.
     */
    private String cityCode;

    /**
     * Nombre de la ciudad.
     */
    private String cityName;

    /**
     * Temperatura actual.
     */
    private BigDecimal temperature;

    /**
     * Condición climática.
     */
    private String weatherCondition;

    /**
     * Porcentaje de humedad.
     */
    private Integer humidity;

    /**
     * Hora de la última actualización.
     */
    private String updatedAt;

    /**
     * Fecha y hora de creación del registro.
     */
    private LocalDateTime createdAt;

    /**
     * Mensaje de estado de la operación.
     */
    private String message;
}
