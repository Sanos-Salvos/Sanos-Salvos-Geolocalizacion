package com.sanossalvos.geolocalizacion.service;

import com.sanossalvos.geolocalizacion.dto.UbicacionDTO;
import java.util.List;

public interface IUbicacionService {
    UbicacionDTO guardar(UbicacionDTO dto);
    List<UbicacionDTO> listarTodas();
    UbicacionDTO buscarPorId(Long id);
    void eliminar(Long id);
}