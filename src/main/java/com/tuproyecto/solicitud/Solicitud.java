package com.tuproyecto.solicitud;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "solicitudes")
public class Solicitud {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false) private String solicitanteUsername;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private TipoSolicitud tipo;
    @Column(nullable = false, columnDefinition = "TEXT") private String descripcion;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private EstadoSolicitud estado = EstadoSolicitud.PENDIENTE;
    private String observacion;
    @Column(nullable = false) private LocalDateTime fechaCreacion;
    private LocalDateTime fechaResolucion;

    @PrePersist
    public void prePersist() {
        this.fechaCreacion = LocalDateTime.now();
        this.estado = EstadoSolicitud.PENDIENTE;
    }

    public Long getId() { return id; }
    public String getSolicitanteUsername() { return solicitanteUsername; }
    public void setSolicitanteUsername(String s) { this.solicitanteUsername = s; }
    public TipoSolicitud getTipo() { return tipo; }
    public void setTipo(TipoSolicitud t) { this.tipo = t; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String d) { this.descripcion = d; }
    public EstadoSolicitud getEstado() { return estado; }
    public void setEstado(EstadoSolicitud e) { this.estado = e; }
    public String getObservacion() { return observacion; }
    public void setObservacion(String o) { this.observacion = o; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public LocalDateTime getFechaResolucion() { return fechaResolucion; }
    public void setFechaResolucion(LocalDateTime f) { this.fechaResolucion = f; }
}
