package com.tuproyecto.solicitud;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SolicitudService {
    private final SolicitudRepository solicitudRepository;

    public SolicitudService(SolicitudRepository solicitudRepository) {
        this.solicitudRepository = solicitudRepository;
    }

    public Solicitud radicar(SolicitudRequest request, String username) {
        Solicitud s = new Solicitud();
        s.setSolicitanteUsername(username);
        s.setTipo(request.getTipo());
        s.setDescripcion(request.getDescripcion());
        return solicitudRepository.save(s);
    }

    public List<Solicitud> getMisSolicitudes(String username) {
        return solicitudRepository.findBySolicitanteUsername(username);
    }

    public List<Solicitud> getTodas() {
        return solicitudRepository.findAll();
    }

    public Solicitud aprobar(Long id, String observacion) {
        Solicitud s = solicitudRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        s.setEstado(EstadoSolicitud.APROBADA);
        s.setObservacion(observacion);
        s.setFechaResolucion(LocalDateTime.now());
        return solicitudRepository.save(s);
    }

    public Solicitud rechazar(Long id, String observacion) {
        Solicitud s = solicitudRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        s.setEstado(EstadoSolicitud.RECHAZADA);
        s.setObservacion(observacion);
        s.setFechaResolucion(LocalDateTime.now());
        return solicitudRepository.save(s);
    }

    public long contarPorEstado(EstadoSolicitud estado) { return solicitudRepository.countByEstado(estado); }
    public long contarTotal() { return solicitudRepository.count(); }
}
