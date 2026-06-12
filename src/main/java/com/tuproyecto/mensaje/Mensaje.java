package com.tuproyecto.mensaje;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "mensajes")
public class Mensaje {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false) private String emisorUsername;
    @Column(nullable = false) private String receptorUsername;
    @Column(nullable = false) private String asunto;
    @Column(nullable = false, columnDefinition = "TEXT") private String contenido;
    @Column(nullable = false) private boolean leido = false;
    @Column(nullable = false) private LocalDateTime fechaEnvio;

    @PrePersist
    public void prePersist() { this.fechaEnvio = LocalDateTime.now(); }

    public Long getId() { return id; }
    public String getEmisorUsername() { return emisorUsername; }
    public void setEmisorUsername(String e) { this.emisorUsername = e; }
    public String getReceptorUsername() { return receptorUsername; }
    public void setReceptorUsername(String r) { this.receptorUsername = r; }
    public String getAsunto() { return asunto; }
    public void setAsunto(String a) { this.asunto = a; }
    public String getContenido() { return contenido; }
    public void setContenido(String c) { this.contenido = c; }
    public boolean isLeido() { return leido; }
    public void setLeido(boolean l) { this.leido = l; }
    public LocalDateTime getFechaEnvio() { return fechaEnvio; }
}
