package com.sanossalvos.geolocalizacion.service;

import com.sanossalvos.geolocalizacion.client.MapaExternoClient;
import com.sanossalvos.geolocalizacion.dto.UbicacionDTO;
import com.sanossalvos.geolocalizacion.factory.IUbicacionFactory;
import com.sanossalvos.geolocalizacion.model.Ubicacion;
import com.sanossalvos.geolocalizacion.producer.UbicacionProducer;
import com.sanossalvos.geolocalizacion.repository.UbicacionRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UbicacionServiceImpl implements IUbicacionService {

    private final UbicacionRepository repository;
    private final MapaExternoClient mapaClient;
    private final IUbicacionFactory factory;
    private final UbicacionProducer producer;


    public UbicacionServiceImpl(UbicacionRepository repository, MapaExternoClient mapaClient,
                                IUbicacionFactory factory, UbicacionProducer producer) {
        this.repository = repository;
        this.mapaClient = mapaClient;
        this.factory = factory;
        this.producer = producer;
    }
    @Override
    public List<UbicacionDTO> listarTodas() {
        return repository.findAll().stream()
                .map(factory::crearDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UbicacionDTO buscarPorId(Long id) {
        Ubicacion u = repository.findById(id).orElseThrow(() -> new RuntimeException("Ubicación no encontrada"));
        return factory.crearDTO(u);
    }

    @Override
    public void eliminar(Long id) {
        repository.deleteById(id);
    }
    @Override
    public UbicacionDTO guardar(UbicacionDTO dto) {

        try {
            List<Map<String, Object>> res = mapaClient.buscarCoordenadas(dto.getDireccion() + ", " + dto.getCiudad());
            if (res != null && !res.isEmpty()) {
                dto.setLatitud(Double.parseDouble(res.get(0).get("lat").toString()));
                dto.setLongitud(Double.parseDouble(res.get(0).get("lon").toString()));
            }
        } catch (Exception e) {
            System.err.println("Error al obtener coordenadas: " + e.getMessage());
        }


        Ubicacion entidad = factory.crearEntidad(dto);


        Ubicacion guardada = repository.save(entidad);


        producer.enviarEventoUbicacion("Nueva ubicación para mascota ID: " + guardada.getMascotaId());


        return factory.crearDTO(guardada);
    }
}