package com.universidad.app.solicitudes.repository;

import com.universidad.app.security.Usuario;
import com.universidad.app.solicitudes.entity.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {

    List<Solicitud> findBySolicitanteOrderByFechaCreacionDesc(Usuario solicitante);

    long countByEstado(Solicitud.EstadoSolicitud estado);
}
