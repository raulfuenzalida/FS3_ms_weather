package duoc.fs3.ms_weather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Clase principal de la aplicación de clima.
 * 
 * Esta clase contiene el método main que inicia el microservicio de clima
 * utilizando Spring Boot. Configura el escaneo de propiedades de configuración
 * y habilita la programación de tareas para el cron job.
 * 
 * @author Duoc UC Fullstack III
 * @version 1.0
 * @since 2026
 */
@SpringBootApplication
@ConfigurationPropertiesScan
@EnableScheduling
public class MsWeatherApplication {

    /**
     * Método principal que inicia la aplicación Spring Boot.
     * 
     * @param args Argumentos de línea de comandos pasados a la aplicación
     */
    public static void main(String[] args) {
        SpringApplication.run(MsWeatherApplication.class, args);
    }

}
