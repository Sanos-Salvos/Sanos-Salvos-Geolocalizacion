package com.sanossalvos.geolocalizacion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UbicacionDTO {
    private Long mascotaId;
    private String direccion;
    private String ciudad;
    private Double latitud;
    private Double longitud;
}