package duoc.fs3.ms_weather.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidad JPA que representa los datos del clima almacenados en la base de datos.
 * 
 * Esta clase mapea la tabla weather_data y contiene la información del clima
 * para cada ciudad de Chile obtenida desde la API externa.
 * 
 * @author Duoc UC Fullstack III
 * @version 1.0
 * @since 2026
 */
@Entity
@Table(name = "weather_data", indexes = {
    @Index(name = "idx_city_code", columnList = "cityCode"),
    @Index(name = "idx_city_name", columnList = "cityName"),
    @Index(name = "idx_created_at", columnList = "createdAt")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherData {

    /**
     * Identificador único del registro.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Código de la ciudad (ej: SCEL, SCIE).
     */
    @Column(name = "city_code", nullable = false, length = 10)
    private String cityCode;

    /**
     * Nombre de la ciudad.
     */
    @Column(name = "city_name", nullable = false, length = 100)
    private String cityName;

    /**
     * Temperatura actual de la ciudad.
     */
    @Column(precision = 5, scale = 2)
    private BigDecimal temperature;

    /**
     * Condición climática actual.
     */
    @Column(name = "weather_condition", length = 100)
    private String weatherCondition;

    /**
     * Porcentaje de humedad.
     */
    @Column
    private Integer humidity;

    /**
     * Hora de la última actualización proporcionada por la API.
     */
    @Column(name = "updated_at")
    private String updatedAt;

    /**
     * Fecha y hora de creación del registro en la base de datos.
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Fecha y hora de la última actualización del registro.
     */
    @UpdateTimestamp
    @Column(name = "updated_timestamp")
    private LocalDateTime updatedAtTimestamp;
}
