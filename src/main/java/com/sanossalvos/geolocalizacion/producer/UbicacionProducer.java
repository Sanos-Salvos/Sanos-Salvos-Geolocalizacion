package com.sanossalvos.geolocalizacion.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class UbicacionProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public UbicacionProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void enviarEventoUbicacion(String mensaje) {
        kafkaTemplate.send("UbicacionRegistrada", mensaje);
    }
}