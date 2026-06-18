package com.sanossalvos.geolocalizacion.producer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UbicacionProducerTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    private UbicacionProducer ubicacionProducer;

    @Test
    void enviarEventoUbicacion_DeberiaEnviarMensajeATopicoReal() {
        // Arrange
        String mensajeJson = "{\"mascotaId\":10,\"ciudad\":\"Santiago\"}";

        // Act
        ubicacionProducer.enviarEventoUbicacion(mensajeJson);

        // Assert
        // Cambiado "notifications" por el tópico real que usa tu productor
        verify(kafkaTemplate, times(1)).send(eq("ubicacion-registrada"), eq(mensajeJson));
    }
}