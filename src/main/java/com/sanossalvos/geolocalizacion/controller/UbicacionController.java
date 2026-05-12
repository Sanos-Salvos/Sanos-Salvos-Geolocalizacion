package com.sanossalvos.geolocalizacion.controller;

import com.sanossalvos.geolocalizacion.dto.UbicacionDTO;
import com.sanossalvos.geolocalizacion.service.IUbicacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/geolocalizacion")
public class UbicacionController {

    private final IUbicacionService service;

    public UbicacionController(IUbicacionService service) {
        this.service = service;
    }

    @PostMapping("/registrar")
    public ResponseEntity<UbicacionDTO> registrar(@RequestBody UbicacionDTO dto) {
        return ResponseEntity.ok(service.guardar(dto));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<UbicacionDTO>> listar() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UbicacionDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }


    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok("Ubicación eliminada correctamente");
    }
}