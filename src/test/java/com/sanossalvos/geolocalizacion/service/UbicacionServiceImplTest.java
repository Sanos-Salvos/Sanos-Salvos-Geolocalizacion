package com.sanossalvos.geolocalizacion.service;

import com.sanossalvos.geolocalizacion.client.MapaExternoClient;
import com.sanossalvos.geolocalizacion.dto.UbicacionDTO;
import com.sanossalvos.geolocalizacion.factory.IUbicacionFactory;
import com.sanossalvos.geolocalizacion.model.Ubicacion;
import com.sanossalvos.geolocalizacion.producer.UbicacionProducer;
import com.sanossalvos.geolocalizacion.repository.UbicacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UbicacionServiceImplTest {

    @Mock
    private UbicacionRepository repository;

    @Mock
    private MapaExternoClient mapaClient;

    @Mock
    private IUbicacionFactory factory;

    @Mock
    private UbicacionProducer producer;

    @InjectMocks
    private UbicacionServiceImpl service;

    private Ubicacion ubicacion;
    private UbicacionDTO dto;

    @BeforeEach
    void setUp() {
        ubicacion = new Ubicacion();
        ubicacion.setId(1L);
        ubicacion.setMascotaId(10L);
        ubicacion.setDireccion("Av. Principal 123");
        ubicacion.setCiudad("Santiago");
        ubicacion.setLatitud(-33.4489);
        ubicacion.setLongitud(-70.6693);

        dto = UbicacionDTO.builder()
                .mascotaId(10L)
                .direccion("Av. Principal 123")
                .ciudad("Santiago")
                .latitud(-33.4489)
                .longitud(-70.6693)
                .build();
    }

    @Test
    void guardar_conCoordenadasExternas_deberiaGuardarYRetornarDTO() {

        Map<String, Object> mapaResultado = new HashMap<>();
        mapaResultado.put("lat", "-33.4489");
        mapaResultado.put("lon", "-70.6693");

        when(mapaClient.buscarCoordenadas(anyString())).thenReturn(List.of(mapaResultado));
        when(factory.crearEntidad(any(UbicacionDTO.class))).thenReturn(ubicacion);
        when(repository.save(any(Ubicacion.class))).thenReturn(ubicacion);
        when(factory.crearDTO(any(Ubicacion.class))).thenReturn(dto);

        UbicacionDTO resultado = service.guardar(dto);

        assertNotNull(resultado);
        assertEquals(-33.4489, resultado.getLatitud());
        assertEquals(-70.6693, resultado.getLongitud());
        verify(producer).enviarEventoUbicacion(anyString());
        verify(repository).save(any(Ubicacion.class));
    }

    @Test
    void guardar_sinResultadosDeMapa_deberiaGuardarSinCoordenadasExternas() {

        when(mapaClient.buscarCoordenadas(anyString())).thenReturn(Collections.emptyList());
        when(factory.crearEntidad(any(UbicacionDTO.class))).thenReturn(ubicacion);
        when(repository.save(any(Ubicacion.class))).thenReturn(ubicacion);
        when(factory.crearDTO(any(Ubicacion.class))).thenReturn(dto);

        UbicacionDTO resultado = service.guardar(dto);

        assertNotNull(resultado);
        verify(repository).save(any(Ubicacion.class));
    }

    @Test
    void guardar_cuandoMapaFalla_deberiaLanzarExcepcion() {
        when(mapaClient.buscarCoordenadas(anyString())).thenThrow(new RuntimeException("API down"));

        assertThrows(RuntimeException.class, () -> {
            service.guardar(dto);
        });
        verify(repository, never()).save(any(Ubicacion.class));
    }

    @Test
    void listarTodas_deberiaRetornarListaDTOs() {

        when(repository.findAll()).thenReturn(List.of(ubicacion));
        when(factory.crearDTO(any(Ubicacion.class))).thenReturn(dto);

        List<UbicacionDTO> resultado = service.listarTodas();

        assertEquals(1, resultado.size());
        verify(repository).findAll();
    }

    @Test
    void listarTodas_cuandoVacia_deberiaRetornarListaVacia() {

        when(repository.findAll()).thenReturn(Collections.emptyList());

        List<UbicacionDTO> resultado = service.listarTodas();

        assertTrue(resultado.isEmpty());
    }

    @Test
    void buscarPorId_existente_deberiaRetornarDTO() {

        when(repository.findById(1L)).thenReturn(Optional.of(ubicacion));
        when(factory.crearDTO(ubicacion)).thenReturn(dto);

        UbicacionDTO resultado = service.buscarPorId(1L);


        assertNotNull(resultado);
        assertEquals(10L, resultado.getMascotaId());
    }

    @Test
    void buscarPorId_noExistente_deberiaLanzarExcepcion() {

        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.buscarPorId(99L));
    }

    @Test
    void eliminar_deberiaEliminarPorId() {
        service.eliminar(1L);

        verify(repository).deleteById(1L);
    }
}
