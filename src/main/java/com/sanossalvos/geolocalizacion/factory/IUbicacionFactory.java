package com.sanossalvos.geolocalizacion.factory;

import com.sanossalvos.geolocalizacion.dto.UbicacionDTO;
import com.sanossalvos.geolocalizacion.model.Ubicacion;

public interface IUbicacionFactory {

    Ubicacion crearEntidad(UbicacionDTO dto);

    UbicacionDTO crearDTO(Ubicacion entidad);
}