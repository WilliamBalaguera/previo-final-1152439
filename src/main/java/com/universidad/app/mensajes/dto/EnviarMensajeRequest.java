package com.universidad.app.mensajes.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EnviarMensajeRequest {

    @NotBlank(message = "El destinatario es obligatorio")
    private String destinatarioUsername;

    @NotBlank(message = "El asunto es obligatorio")
    private String asunto;

    @NotBlank(message = "El contenido es obligatorio")
    private String contenido;
}
