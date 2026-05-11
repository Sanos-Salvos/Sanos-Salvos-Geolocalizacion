package com.sanossalvos.geolocalizacion.dto;

import lombok.Data;

@Data
public class UbicacionDTO {
    private Long mascotaId;
    private Double latitud;
    private Double longitud;
    private String direccion;
    private String ciudad;
}