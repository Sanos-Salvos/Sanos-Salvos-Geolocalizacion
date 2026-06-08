# Sanos-Salvos-Geolocalizacion

Servicio de ubicacion y geolocalizacion

## Puerto

8085

## Base de datos

geolocalizacion_db

## Endpoints disponibles

GET /api/ubicaciones
POST /api/ubicaciones
GET /api/ubicaciones/{id}

## Ejecucion con Docker

docker-compose up --build

## Ejecucion manual

mvn spring-boot:run

## Tecnologias

- Java 21
- Spring Boot 3.2
- Spring Security + JWT
- PostgreSQL
- Docker
