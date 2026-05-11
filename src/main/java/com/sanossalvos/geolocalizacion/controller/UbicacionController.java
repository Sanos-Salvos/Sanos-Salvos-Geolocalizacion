package com.sanossalvos.geolocalizacion.controller;

import com.sanossalvos.geolocalizacion.dto.UbicacionDTO;
import com.sanossalvos.geolocalizacion.model.Ubicacion;
import com.sanossalvos.geolocalizacion.service.UbicacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/geolocalizacion")
public class UbicacionController {
    private final UbicacionService service;

    public UbicacionController(UbicacionService service) {
        this.service = service;
    }

    @PostMapping("/registrar")
    public ResponseEntity<Ubicacion> registrar(@RequestBody UbicacionDTO dto) {
        // Ahora el nombre 'guardar' coincide con el del Service
        return ResponseEntity.ok(service.guardar(dto));
    }
}