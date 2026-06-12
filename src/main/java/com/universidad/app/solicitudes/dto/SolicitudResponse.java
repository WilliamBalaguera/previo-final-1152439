package com.universidad.app.solicitudes.dto;

import com.universidad.app.solicitudes.entity.Solicitud;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data @Builder
public class SolicitudResponse {
    private Long id;
    private String solicitante;
    private Solicitud.TipoSolicitud tipo;
    private String descripcion;
    private Solicitud.EstadoSolicitud estado;
    private String observacion;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaResolucion;
}
