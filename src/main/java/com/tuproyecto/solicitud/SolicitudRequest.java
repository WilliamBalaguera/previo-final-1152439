package com.tuproyecto.solicitud;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SolicitudRequest {
    @NotNull(message = "El tipo es obligatorio")
    private TipoSolicitud tipo;
    @NotBlank(message = "La descripcion es obligatoria")
    private String descripcion;

    public TipoSolicitud getTipo() { return tipo; }
    public void setTipo(TipoSolicitud t) { this.tipo = t; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String d) { this.descripcion = d; }
}
