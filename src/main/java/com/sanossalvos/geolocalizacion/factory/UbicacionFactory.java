package com.sanossalvos.geolocalizacion.factory;

import com.sanossalvos.geolocalizacion.dto.UbicacionDTO;
import com.sanossalvos.geolocalizacion.model.Ubicacion;

public class UbicacionFactory {
    public static Ubicacion crearDesdeDTO(UbicacionDTO dto) {
        Ubicacion ubicacion = new Ubicacion();
        ubicacion.setMascotaId(dto.getMascotaId());
        ubicacion.setLatitud(dto.getLatitud());
        ubicacion.setLongitud(dto.getLongitud());
        ubicacion.setDireccion(dto.getDireccion());
        ubicacion.setCiudad(dto.getCiudad());
        return ubicacion;
    }
}