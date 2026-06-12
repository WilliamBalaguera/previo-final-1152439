package com.universidad.app.solicitudes.dto;

import com.universidad.app.solicitudes.entity.Solicitud;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CrearSolicitudRequest {

    @NotNull(message = "El tipo es obligatorio")
    private Solicitud.TipoSolicitud tipo;

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;
}
