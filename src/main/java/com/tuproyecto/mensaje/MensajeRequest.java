package com.tuproyecto.mensaje;

import jakarta.validation.constraints.NotBlank;

public class MensajeRequest {
    @NotBlank(message = "El destinatario es obligatorio")
    private String destinatarioUsername;
    @NotBlank(message = "El asunto es obligatorio")
    private String asunto;
    @NotBlank(message = "El contenido es obligatorio")
    private String contenido;

    public String getDestinatarioUsername() { return destinatarioUsername; }
    public void setDestinatarioUsername(String d) { this.destinatarioUsername = d; }
    public String getAsunto() { return asunto; }
    public void setAsunto(String a) { this.asunto = a; }
    public String getContenido() { return contenido; }
    public void setContenido(String c) { this.contenido = c; }
}
