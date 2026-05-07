-- Script de configuración de base de datos para ms-weather
-- Ejecutar este script en MySQL para crear la base de datos y tabla necesaria

-- Crear base de datos si no existe
CREATE DATABASE IF NOT EXISTS weather_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

-- Usar la base de datos
USE weather_db;

-- Crear tabla weather_data
CREATE TABLE IF NOT EXISTS weather_data (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    city_code VARCHAR(10) NOT NULL COMMENT 'Código de la ciudad (ej: SCEL, SCIE)',
    city_name VARCHAR(100) NOT NULL COMMENT 'Nombre de la ciudad',
    temperature DECIMAL(5,2) COMMENT 'Temperatura actual en grados',
    weather_condition VARCHAR(100) COMMENT 'Condición climática actual',
    humidity INT COMMENT 'Porcentaje de humedad',
    updated_at TIME COMMENT 'Hora de la última actualización de la API',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Fecha y hora de creación del registro',
    updated_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Fecha y hora de última actualización del registro',
    
    -- Índices para optimizar consultas
    INDEX idx_city_code (city_code),
    INDEX idx_city_name (city_name),
    INDEX idx_created_at (created_at),
    INDEX idx_city_code_created (city_code, created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Opcional: Insertar datos de ejemplo para pruebas
-- (El cron job insertará datos automáticamente, pero estos son para pruebas iniciales)

-- Mostrar la estructura de la tabla
DESCRIBE weather_data;

-- Mostrar bases de datos disponibles
SHOW DATABASES LIKE 'weather%';

-- Confirmar que la tabla fue creada correctamente
SELECT 
    TABLE_NAME,
    TABLE_COMMENT,
    ENGINE,
    TABLE_COLLATION
FROM information_schema.TABLES 
WHERE TABLE_SCHEMA = 'weather_db' 
AND TABLE_NAME = 'weather_data';
