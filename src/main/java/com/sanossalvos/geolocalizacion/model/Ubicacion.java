package com.sanossalvos.geolocalizacion.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ubicaciones")
@Data
public class Ubicacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long mascotaId;
    private String direccion;
    private String ciudad;
    private Double latitud;
    private Double longitud;
}