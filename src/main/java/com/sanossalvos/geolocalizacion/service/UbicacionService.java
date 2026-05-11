package com.sanossalvos.geolocalizacion.service;

import com.sanossalvos.geolocalizacion.client.MapaExternoClient;
import com.sanossalvos.geolocalizacion.dto.UbicacionDTO;
import com.sanossalvos.geolocalizacion.model.Ubicacion;
import com.sanossalvos.geolocalizacion.repository.UbicacionRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class UbicacionService {
    private final UbicacionRepository repository;
    private final MapaExternoClient mapaClient;

    public UbicacionService(UbicacionRepository repository, MapaExternoClient mapaClient) {
        this.repository = repository;
        this.mapaClient = mapaClient;
    }

    public Ubicacion guardar(UbicacionDTO dto) {
        try {
            // Consultamos a la API externa de OpenStreetMap
            List<Map<String, Object>> resultado = mapaClient.buscarCoordenadas(dto.getDireccion() + ", " + dto.getCiudad());

            if (resultado != null && !resultado.isEmpty()) {
                // Extraemos latitud y longitud devueltas por el servicio externo
                Double lat = Double.parseDouble(resultado.get(0).get("lat").toString());
                Double lon = Double.parseDouble(resultado.get(0).get("lon").toString());
                dto.setLatitud(lat);
                dto.setLongitud(lon);
            }
        } catch (Exception e) {
            System.err.println("Error consultando API de mapas: " + e.getMessage());
            // Si la API falla, guardará con las coordenadas nulas o las que vengan del DTO
        }

        // Mapeo manual de DTO a Entidad
        Ubicacion entidad = new Ubicacion();
        entidad.setMascotaId(dto.getMascotaId());
        entidad.setDireccion(dto.getDireccion());
        entidad.setCiudad(dto.getCiudad());
        entidad.setLatitud(dto.getLatitud());
        entidad.setLongitud(dto.getLongitud());

        return repository.save(entidad);
    }
}