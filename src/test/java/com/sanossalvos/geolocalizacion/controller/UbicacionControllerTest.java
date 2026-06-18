package com.sanossalvos.geolocalizacion.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanossalvos.geolocalizacion.dto.UbicacionDTO;
import com.sanossalvos.geolocalizacion.service.IUbicacionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UbicacionController.class)
@AutoConfigureMockMvc(addFilters = false) // Evita interferencias de Spring Security
class UbicacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUbicacionService service;

    @Autowired
    private ObjectMapper objectMapper;

    private UbicacionDTO dto;

    @BeforeEach
    void setUp() {
        dto = UbicacionDTO.builder()
                .mascotaId(10L)
                .direccion("Av. Principal 123")
                .ciudad("Santiago")
                .latitud(-33.4489)
                .longitud(-70.6693)
                .build();
    }

    @Test
    void registrar_deberiaRetornarOKConJson() throws Exception {
        when(service.guardar(any(UbicacionDTO.class))).thenReturn(dto);

        mockMvc.perform(post("/api/geolocalizacion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ciudad").value("Santiago"));
    }

    @Test
    void listar_deberiaRetornarListaDeUbicaciones() throws Exception {
        when(service.listarTodas()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/geolocalizacion"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].mascotaId").value(10));
    }

    @Test
    void listar_cuandoVacia_deberiaRetornarVacio() throws Exception {
        when(service.listarTodas()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/geolocalizacion"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void obtenerPorId_existente_deberiaRetornarUbicacion() throws Exception {
        when(service.buscarPorId(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/geolocalizacion/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mascotaId").value(10));
    }

    @Test
    void eliminar_deberiaRetornarMensajeString() throws Exception {
        doNothing().when(service).eliminar(1L);

        mockMvc.perform(delete("/api/geolocalizacion/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Ubicación eliminada correctamente")); // <-- Modificado aquí
    }
}