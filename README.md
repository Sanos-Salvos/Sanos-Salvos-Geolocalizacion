# Sanos-Salvos-Geolocalizacion

Microservicio especializado en la gestión de coordenadas y rastreo de mascotas en tiempo real, integrando APIs externas de mapas (OpenStreetMap) dentro del ecosistema Sanos y Salvos.

## Requisitos
* Java JDK 17
* Maven 3.8+
* Conexión a Internet (para API Nominatim/OSM)

## Instalación
1. Clonar repositorio:
   `git clone https://github.com/TU_USUARIO/Sanos-Salvos-Geolocalizacion.git`
   `cd Sanos-Salvos-Geolocalizacion`

2. Instalar dependencias:
   `.\mvnw clean install` (Windows) o `./mvnw clean install` (Linux/Mac)

## Configuración
Archivo: `src/main/resources/application.properties`
```properties
server.port=8082
spring.application.name=geolocalizacion-service

# Configuración de Feign para Mapas Externos
osm.api.url=[https://nominatim.openstreetmap.org/](https://nominatim.openstreetmap.org/)
