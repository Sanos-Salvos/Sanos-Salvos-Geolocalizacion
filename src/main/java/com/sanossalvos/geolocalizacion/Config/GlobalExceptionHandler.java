package com.sanossalvos.geolocalizacion.Config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception ex) {
        return ResponseEntity.internalServerError().body("Error en el Microservicio de Geolocalización: " + ex.getMessage());
    }
}