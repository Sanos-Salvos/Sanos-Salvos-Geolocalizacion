package com.sanossalvos.geolocalizacion.factory;

import com.sanossalvos.geolocalizacion.dto.UbicacionDTO;
import com.sanossalvos.geolocalizacion.model.Ubicacion;
import org.springframework.stereotype.Component;

@Component
public class UbicacionFactoryImpl implements IUbicacionFactory {

    @Override
    public Ubicacion crearEntidad(UbicacionDTO dto) {
        Ubicacion u = new Ubicacion();
        u.setMascotaId(dto.getMascotaId());
        u.setDireccion(dto.getDireccion());
        u.setCiudad(dto.getCiudad());
        u.setLatitud(dto.getLatitud());
        u.setLongitud(dto.getLongitud());
        return u;
    }

    @Override
    public UbicacionDTO crearDTO(Ubicacion u) {
        UbicacionDTO dto = new UbicacionDTO();
        dto.setMascotaId(u.getMascotaId());
        dto.setDireccion(u.getDireccion());
        dto.setCiudad(u.getCiudad());
        dto.setLatitud(u.getLatitud());
        dto.setLongitud(u.getLongitud());
        return dto;
    }
}