package com.universidad.app.mensajes.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data @Builder
public class MensajeResponse {
    private Long id;
    private String emisor;
    private String receptor;
    private String asunto;
    private String contenido;
    private boolean leido;
    private LocalDateTime fechaEnvio;
}
