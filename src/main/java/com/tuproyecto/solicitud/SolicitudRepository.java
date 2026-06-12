package com.tuproyecto.solicitud;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {
    List<Solicitud> findBySolicitanteUsername(String username);
    long countByEstado(EstadoSolicitud estado);
}
