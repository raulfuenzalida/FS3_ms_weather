package duoc.fs3.ms_weather.repository;

import duoc.fs3.ms_weather.model.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio JPA para la gestión de datos del clima.
 * 
 * Esta interfaz proporciona métodos para realizar operaciones CRUD
 * y consultas personalizadas sobre los datos del clima.
 * 
 * @author Duoc UC Fullstack III
 * @version 1.0
 * @since 2026
 */
@Repository
public interface WeatherRepository extends JpaRepository<WeatherData, Long> {

    /**
     * Busca datos del clima por nombre de ciudad.
     * 
     * @param cityName Nombre de la ciudad a buscar
     * @return Optional con los datos del clima si existen
     */
    Optional<WeatherData> findByCityNameIgnoreCase(String cityName);

    /**
     * Busca datos del clima por código de ciudad.
     * 
     * @param cityCode Código de la ciudad a buscar
     * @return Optional con los datos del clima si existen
     */
    Optional<WeatherData> findByCityCode(String cityCode);

    /**
     * Busca el registro más reciente para una ciudad específica.
     * 
     * @param cityName Nombre de la ciudad
     * @return Optional con el registro más reciente si existe
     */
    @Query("SELECT w FROM WeatherData w WHERE w.cityName = :cityName ORDER BY w.createdAt DESC")
    Optional<WeatherData> findLatestByCityName(@Param("cityName") String cityName);

    /**
     * Busca el registro más reciente para un código de ciudad específico.
     * 
     * @param cityCode Código de la ciudad
     * @return Optional con el registro más reciente si existe
     */
    @Query("SELECT w FROM WeatherData w WHERE w.cityCode = :cityCode ORDER BY w.createdAt DESC")
    Optional<WeatherData> findLatestByCityCode(@Param("cityCode") String cityCode);

    /**
     * Obtiene todos los registros más recientes de cada ciudad.
     * 
     * @return Lista con los datos más recientes por ciudad
     */
    @Query("SELECT w FROM WeatherData w WHERE w.id IN " +
           "(SELECT MAX(w2.id) FROM WeatherData w2 GROUP BY w2.cityCode)")
    List<WeatherData> findAllLatest();

    /**
     * Obtiene todos los registros de una ciudad en un rango de fechas.
     * 
     * @param cityName Nombre de la ciudad
     * @param startDate Fecha de inicio
     * @param endDate Fecha de fin
     * @return Lista de registros en el rango especificado
     */
    @Query("SELECT w FROM WeatherData w WHERE w.cityName = :cityName " +
           "AND w.createdAt BETWEEN :startDate AND :endDate ORDER BY w.createdAt DESC")
    List<WeatherData> findByCityNameAndDateRange(@Param("cityName") String cityName,
                                                @Param("startDate") LocalDateTime startDate,
                                                @Param("endDate") LocalDateTime endDate);

    /**
     * Verifica si existe un registro para un código de ciudad específico.
     * 
     * @param cityCode Código de la ciudad
     * @return true si existe, false en caso contrario
     */
    boolean existsByCityCode(String cityCode);

    /**
     * Obtiene todos los registros ordenados por fecha de creación descendente.
     * 
     * @return Lista de todos los registros ordenados
     */
    @Query("SELECT w FROM WeatherData w ORDER BY w.createdAt DESC")
    List<WeatherData> findAllOrderByCreatedAtDesc();

    /**
     * Elimina registros antiguos basados en una fecha límite.
     * 
     * @param cutoffDate Fecha límite para eliminar registros
     * @return Número de registros eliminados
     */
    @Query("DELETE FROM WeatherData w WHERE w.createdAt < :cutoffDate")
    int deleteOldRecords(@Param("cutoffDate") LocalDateTime cutoffDate);
}
