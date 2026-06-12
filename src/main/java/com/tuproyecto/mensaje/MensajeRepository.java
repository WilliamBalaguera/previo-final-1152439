package com.tuproyecto.mensaje;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MensajeRepository extends JpaRepository<Mensaje, Long> {
    List<Mensaje> findByReceptorUsername(String receptorUsername);
    List<Mensaje> findByEmisorUsername(String emisorUsername);
    long countByReceptorUsernameAndLeidoFalse(String receptorUsername);
}
