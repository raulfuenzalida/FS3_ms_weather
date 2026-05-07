# ms-weather - Microservicio de Clima

Microservicio de gestión de datos del clima de Chile desarrollado con Spring Boot 3.5.13, Java 17 y Maven. Proporciona funcionalidades de recolección automática de datos mediante cron job y endpoints REST para consulta.

## 🌟 Características

- **Cron Job Automático**: Recolecta datos del clima desde API externa diariamente a las 9:20 AM
- **Base de Datos MySQL**: Almacena datos de todas las ciudades de Chile en `weather_db`
- **Endpoints REST**: API para consulta de datos del clima desde frontend
- **Configuración Flexible**: Hora de ejecución del cron configurable desde `application.properties`
- **Documentación Swagger**: API documentada automáticamente con OpenAPI 3.0
- **Arquitectura Limpia**: Separación de responsabilidades clara
- **CORS Configurado**: Habilitado para frontend (localhost:3000, 4200, 8081)
- **Logging Estructurado**: Logs detallados para monitoreo y debugging

## 🛠 Tecnologías

- **Java 17**
- **Spring Boot 3.5.13**
- **Spring Data JPA** para acceso a datos
- **MySQL** como base de datos
- **Spring Scheduling** para cron jobs
- **SpringDoc OpenAPI** para documentación de API
- **Lombok** para reducir código boilerplate

## 📋 Requisitos Previos

- **Java 17** o superior
- **Maven 3.8+**
- **MySQL Server** (configurado con Laragon para desarrollo local)
- **IDE compatible** con Java (IntelliJ IDEA, Eclipse, VS Code)

## 🗄 Configuración de Base de Datos

### 1. Crear la base de datos
```sql
CREATE DATABASE weather_db;
```

### 2. Ejecutar script SQL (opcional)
```bash
mysql -u root -p < database-setup.sql
```

### 3. Configuración automática
La aplicación se conectará automáticamente a `localhost:3306/weather_db` con el usuario `root` y sin contraseña (configuración por defecto de Laragon).

## 🚀 Ejecución de la Aplicación

### Desde Maven
```bash
cd ms-weather
mvn clean install
mvn spring-boot:run
```

### Desde IDE
1. Importar el proyecto como proyecto Maven
2. Ejecutar la clase `MsWeatherApplication.java`
3. La aplicación estará disponible en `http://localhost:8081`

### Verificación
```bash
curl http://localhost:8081/api/weather/health
# Respuesta esperada: "ms-weather service is running"
```

## ⏰ Configuración del Cron Job

El cron job se ejecuta diariamente a las **9:20 AM** según la configuración actual:

```properties
weather.cron.expression=0 20 9 * * *
```

### Cambiar la hora de ejecución
Edita `application.properties` y modifica la expresión cron:

```properties
# Para ejecutar a las 8:00 AM
weather.cron.expression=0 0 8 * * *

# Para ejecutar a las 2:00 PM
weather.cron.expression=0 0 14 * * *

# Para ejecutar a medianoche
weather.cron.expression=0 0 0 * * *
```

### Formato de expresión cron
```
Segundos Minutos Hora Día-mes Mes Día-semana
0        20      9    *      *   *
```

## 🌐 Endpoints de la API

### Consulta de Clima

#### Obtener clima por ciudad
```http
GET /api/weather/city/{cityName}
```
**Ejemplo**: `GET /api/weather/city/Santiago`

**Respuesta**:
```json
{
  "id": 1,
  "cityCode": "SCEL",
  "cityName": "Santiago Poniente",
  "temperature": 10.0,
  "weatherCondition": "Nublado",
  "humidity": 87,
  "updatedAt": "08:00:00",
  "createdAt": "2026-05-07T09:20:00",
  "message": "Datos del clima encontrados exitosamente"
}
```

#### Obtener clima por código
```http
GET /api/weather/code/{cityCode}
```
**Ejemplo**: `GET /api/weather/code/SCEL`

#### Obtener todos los climas más recientes
```http
GET /api/weather/all/latest
```

#### Obtener todos los climas
```http
GET /api/weather/all
```

#### Obtener historial de clima
```http
GET /api/weather/history/{cityName}?startDate=2026-01-01&endDate=2026-01-31
```

#### Health Check
```http
GET /api/weather/health
```

## 📚 Documentación de la API

Una vez que la aplicación esté en ejecución, puedes acceder a:

- **Swagger UI**: `http://localhost:8081/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8081/v3/api-docs`

## 🏗 Estructura del Proyecto

```
src/main/java/duoc/fs3/ms_weather/
|-- MsWeatherApplication.java      # Clase principal
|-- config/                         # Configuraciones
|   |-- OpenApiConfig.java          # Configuración Swagger/OpenAPI
|   |-- WebConfig.java              # Configuración CORS y MVC
|   |-- RestTemplateConfig.java    # Configuración RestTemplate
|-- controller/
|   |-- WeatherController.java      # Endpoints REST
|-- cron/                           # Servicios de cron job
|   |-- WeatherApiService.java      # Consumo de API externa
|   |-- WeatherCronService.java     # Lógica del cron job
|-- model/
|   |-- WeatherData.java           # Entidad JPA
|   |-- WeatherApiResponse.java    # DTO de API externa
|-- repository/
|   |-- WeatherRepository.java      # Repositorio JPA
|-- service/
|   |-- WeatherService.java         # Servicio de lógica de negocio
|-- dto/
|   |-- response/                   # DTOs de respuesta
|       |-- WeatherResponse.java
```

## 🎯 Arquitectura del Microservicio

### Separación de Responsabilidades
El microservicio sigue una arquitectura limpia con separación clara de responsabilidades:

- **Controller**: Manejo de solicitudes HTTP y respuestas
- **Service**: Lógica de negocio centralizada
- **Repository**: Acceso a datos con Spring Data JPA
- **Cron**: Tareas programadas y consumo de API externa

### Flujo de Datos
1. **Cron Job** (diario a las 9:20 AM) → Llama a API externa → Almacena en base de datos
2. **Frontend** → Llama a endpoints REST → Consulta datos almacenados

## ⚙️ Configuración

### application.properties
Configuración principal incluyendo conexión a base de datos y expresión cron.

### Variables de entorno importantes
```properties
# Base de datos
spring.datasource.url=jdbc:mysql://localhost:3306/weather_db

# Cron job (actualmente 9:20 AM)
weather.cron.expression=0 20 9 * * *

# API externa
weather.api.url=https://api.boostr.cl/weather.json
```

## 📝 Logging y Monitoreo

La aplicación utiliza logging estructurado con SLF4J:

### Niveles de logging
- **INFO**: Operaciones normales (inicio de aplicación, ejecución de cron)
- **DEBUG**: Detalles de peticiones HTTP
- **ERROR**: Problemas y excepciones

### Logs importantes del cron job
```
INFO - Iniciando actualización automática de datos del clima
INFO - Intento 1 de actualización de datos del clima
INFO - Obteniendo datos del clima desde la API: https://api.boostr.cl/weather.json
INFO - Datos del clima obtenidos exitosamente. Total de ciudades: 32
INFO - Se guardaron 32 registros de datos del clima
INFO - Actualización de datos del clima completada exitosamente
```

## 🧪 Pruebas Unitarias

Para ejecutar las pruebas unitarias:

```bash
mvn test
```

Las pruebas cubren:
- Controladores REST
- Servicios de lógica de negocio
- Repositorios JPA
- Servicios de API externa

## 🔧 Troubleshooting

### Problemas Comunes

#### 1. Error de conexión a base de datos
```
Could not create connection to database server
```
**Solución**: Verifica que MySQL esté corriendo y que la base de datos `weather_db` exista.

#### 2. Error de propiedad no encontrada
```
Could not resolve placeholder 'weather.api.url'
```
**Solución**: Reinicia la aplicación después de modificar `application.properties`.

#### 3. Error de palabra reservada en SQL
```
You have an error in your SQL syntax; check the manual...
```
**Solución**: El script SQL ya está corregido usando `weather_condition` en lugar de `condition`.

#### 4. El cron job no se ejecuta
**Solución**: Verifica la expresión cron en `application.properties` y reinicia la aplicación.

### Verificación del funcionamiento
```bash
# Verificar que la aplicación está corriendo
curl http://localhost:8081/api/weather/health

# Verificar que hay datos en la base de datos
curl http://localhost:8081/api/weather/all/latest
```

## 🔄 Desarrollo

### Agregar nuevas funcionalidades

1. Crear las clases necesarias en los paquetes correspondientes
2. Agregar pruebas unitarias
3. Actualizar la documentación con anotaciones Swagger
4. Actualizar este README si es necesario

### Estándares de Código

- Todo el código está documentado con JavaDoc en español
- Se siguen las convenciones de nomenclatura de Java
- Las pruebas unitarias deben cubrir al menos los casos principales
- Se utiliza Lombok para reducir código boilerplate

## 📄 Licencia

MIT License - Ver archivo LICENSE para más detalles.

## 👤 Autor

Desarrollado por Duoc UC - Fullstack III (2026)
