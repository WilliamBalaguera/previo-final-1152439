package com.universidad.app.solicitudes.service;

import com.universidad.app.security.Usuario;
import com.universidad.app.security.UsuarioRepository;
import com.universidad.app.solicitudes.dto.CrearSolicitudRequest;
import com.universidad.app.solicitudes.dto.SolicitudResponse;
import com.universidad.app.solicitudes.entity.Solicitud;
import com.universidad.app.solicitudes.repository.SolicitudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SolicitudService {

    private final SolicitudRepository solicitudRepository;
    private final UsuarioRepository usuarioRepository;

    public SolicitudResponse crear(String username, CrearSolicitudRequest request) {
        Usuario solicitante = getUsuario(username);
        Solicitud solicitud = Solicitud.builder()
                .solicitante(solicitante)
                .tipo(request.getTipo())
                .descripcion(request.getDescripcion())
                .build();
        return toResponse(solicitudRepository.save(solicitud));
    }

    public List<SolicitudResponse> getMisSolicitudes(String username) {
        Usuario usuario = getUsuario(username);
        return solicitudRepository.findBySolicitanteOrderByFechaCreacionDesc(usuario)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<SolicitudResponse> getTodas() {
        return solicitudRepository.findAll()
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    public SolicitudResponse aprobar(Long id, String observacion) {
        return resolver(id, observacion, Solicitud.EstadoSolicitud.APROBADA);
    }

    public SolicitudResponse rechazar(Long id, String observacion) {
        return resolver(id, observacion, Solicitud.EstadoSolicitud.RECHAZADA);
    }

    private SolicitudResponse resolver(Long id, String observacion, Solicitud.EstadoSolicitud nuevoEstado) {
        Solicitud solicitud = solicitudRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Solicitud no encontrada"));
        solicitud.setEstado(nuevoEstado);
        solicitud.setObservacion(observacion);
        solicitud.setFechaResolucion(LocalDateTime.now());
        return toResponse(solicitudRepository.save(solicitud));
    }

    // Para el panel (Módulo 3)
    public long contarPorEstado(Solicitud.EstadoSolicitud estado) {
        return solicitudRepository.countByEstado(estado);
    }

    public long contarTotal() {
        return solicitudRepository.count();
    }

    private Usuario getUsuario(String username) {
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
    }

    public SolicitudResponse toResponse(Solicitud s) {
        return SolicitudResponse.builder()
                .id(s.getId())
                .solicitante(s.getSolicitante().getUsername())
                .tipo(s.getTipo())
                .descripcion(s.getDescripcion())
                .estado(s.getEstado())
                .observacion(s.getObservacion())
                .fechaCreacion(s.getFechaCreacion())
                .fechaResolucion(s.getFechaResolucion())
                .build();
    }
}
