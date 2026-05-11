package com.sanossalvos.geolocalizacion.repository;

import com.sanossalvos.geolocalizacion.model.Ubicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UbicacionRepository extends JpaRepository<Ubicacion, Long> {
}