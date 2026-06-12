package com.universidad.app.mensajes.repository;

import com.universidad.app.mensajes.entity.Mensaje;
import com.universidad.app.security.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MensajeRepository extends JpaRepository<Mensaje, Long> {

    List<Mensaje> findByReceptorOrderByFechaEnvioDesc(Usuario receptor);

    List<Mensaje> findByEmisorOrderByFechaEnvioDesc(Usuario emisor);

    long countByReceptorAndLeidoFalse(Usuario receptor);
}
