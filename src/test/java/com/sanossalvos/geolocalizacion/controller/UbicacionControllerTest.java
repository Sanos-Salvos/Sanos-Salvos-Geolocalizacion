package com.sanossalvos.geolocalizacion.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanossalvos.geolocalizacion.dto.UbicacionDTO;
import com.sanossalvos.geolocalizacion.service.IUbicacionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UbicacionControllerTest {

    @Mock
    private IUbicacionService service;

    @InjectMocks
    private UbicacionController controller;

    private UbicacionDTO buildDTO() {
        return UbicacionDTO.builder()
                .mascotaId(10L)
                .direccion("Av. Principal 123")
                .ciudad("Santiago")
                .latitud(-33.4489)
                .longitud(-70.6693)
                .build();
    }

    @Test
    void registrar_deberiaRetornarDTO() {
        UbicacionDTO dto = buildDTO();
        when(service.guardar(any(UbicacionDTO.class))).thenReturn(dto);

        ResponseEntity<UbicacionDTO> response = controller.registrar(dto);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("Santiago", response.getBody().getCiudad());
        verify(service).guardar(dto);
    }

    @Test
    void listar_deberiaRetornarLista() {
        when(service.listarTodas()).thenReturn(List.of(buildDTO()));

        ResponseEntity<List<UbicacionDTO>> response = controller.listar();

        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void listar_cuandoVacia_deberiaRetornarListaVacia() {
        when(service.listarTodas()).thenReturn(Collections.emptyList());

        ResponseEntity<List<UbicacionDTO>> response = controller.listar();

        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void obtenerPorId_existente_deberiaRetornarDTO() {
        when(service.buscarPorId(1L)).thenReturn(buildDTO());

        ResponseEntity<UbicacionDTO> response = controller.obtenerPorId(1L);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    @Test
    void eliminar_deberiaRetornarMensajeExito() {
        doNothing().when(service).eliminar(1L);

        ResponseEntity<String> response = controller.eliminar(1L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Ubicación eliminada correctamente", response.getBody());
        verify(service).eliminar(1L);
    }
}
