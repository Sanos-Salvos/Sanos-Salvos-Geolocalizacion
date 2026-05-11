package com.sanossalvos.geolocalizacion.service;

import com.sanossalvos.geolocalizacion.dto.UbicacionDTO;
import com.sanossalvos.geolocalizacion.factory.UbicacionFactory;
import com.sanossalvos.geolocalizacion.model.Ubicacion;
import com.sanossalvos.geolocalizacion.producer.UbicacionProducer;
import com.sanossalvos.geolocalizacion.repository.UbicacionRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;

@Service
public class UbicacionService {
    private final UbicacionRepository repository;
    private final UbicacionProducer producer;

    public UbicacionService(UbicacionRepository repository, UbicacionProducer producer) {
        this.repository = repository;
        this.producer = producer;
    }

    @CircuitBreaker(name = "geoServiceCB", fallbackMethod = "fallbackGeo")
    public Ubicacion guardar(UbicacionDTO dto) {
        Ubicacion ubicacion = UbicacionFactory.crearDesdeDTO(dto);
        Ubicacion guardada = repository.save(ubicacion);
        producer.enviarEventoUbicacion("Nueva ubicación para mascota: " + guardada.getMascotaId());
        return guardada;
    }

    public Ubicacion fallbackGeo(UbicacionDTO dto, Throwable t) {
        System.out.println("Circuit Breaker activo: " + t.getMessage());
        return new Ubicacion();
    }
}