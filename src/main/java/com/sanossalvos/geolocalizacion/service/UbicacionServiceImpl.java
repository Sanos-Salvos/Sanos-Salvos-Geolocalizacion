package com.sanossalvos.geolocalizacion.service;

import com.sanossalvos.geolocalizacion.client.MapaExternoClient;
import com.sanossalvos.geolocalizacion.dto.UbicacionDTO;
import com.sanossalvos.geolocalizacion.factory.IUbicacionFactory;
import com.sanossalvos.geolocalizacion.model.Ubicacion;
import com.sanossalvos.geolocalizacion.producer.UbicacionProducer;
import com.sanossalvos.geolocalizacion.repository.UbicacionRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
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
    @CircuitBreaker(name = "mapasCB", fallbackMethod = "fallbackGuardar")
    public UbicacionDTO guardar(UbicacionDTO dto) {
        List<Map<String, Object>> res = mapaClient.buscarCoordenadas(dto.getDireccion() + ", " + dto.getCiudad());
        if (res != null && !res.isEmpty()) {
            dto.setLatitud(Double.parseDouble(res.get(0).get("lat").toString()));
            dto.setLongitud(Double.parseDouble(res.get(0).get("lon").toString()));
        }
        return procesarYGuardar(dto);
    }
    public UbicacionDTO fallbackGuardar(UbicacionDTO dto, Throwable t) {
        System.err.println("Alerta: API externa de mapas caída o lenta. Aplicando plan B. Motivo: " + t.getMessage());
        dto.setLatitud(0.0);
        dto.setLongitud(0.0);

        return procesarYGuardar(dto);
    }

    private UbicacionDTO procesarYGuardar(UbicacionDTO dto) {
        Ubicacion entidad = factory.crearEntidad(dto);
        Ubicacion guardada = repository.save(entidad);
        producer.enviarEventoUbicacion("Nueva ubicación para mascota ID: " + guardada.getMascotaId());

        return factory.crearDTO(guardada);
    }
}