package com.universidad.app.solicitudes.controller;

import com.universidad.app.solicitudes.dto.CrearSolicitudRequest;
import com.universidad.app.solicitudes.dto.SolicitudResponse;
import com.universidad.app.solicitudes.service.SolicitudService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/solicitudes")
@RequiredArgsConstructor
public class SolicitudController {

    private final SolicitudService solicitudService;

    // POST /api/solicitudes → 201
    @PostMapping
    public ResponseEntity<SolicitudResponse> crear(
            @Valid @RequestBody CrearSolicitudRequest request,
            Principal principal) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(solicitudService.crear(principal.getName(), request));
    }

    // GET /api/solicitudes/mis-solicitudes → 200 (autenticado)
    @GetMapping("/mis-solicitudes")
    public ResponseEntity<List<SolicitudResponse>> getMisSolicitudes(Principal principal) {
        return ResponseEntity.ok(solicitudService.getMisSolicitudes(principal.getName()));
    }

    // GET /api/solicitudes → 200 (solo ADMIN)
    @GetMapping
    public ResponseEntity<List<SolicitudResponse>> getTodas() {
        return ResponseEntity.ok(solicitudService.getTodas());
    }

    // PUT /api/solicitudes/{id}/aprobar → 200 (solo ADMIN)
    @PutMapping("/{id}/aprobar")
    public ResponseEntity<SolicitudResponse> aprobar(
            @PathVariable Long id,
            @RequestParam String observacion) {
        return ResponseEntity.ok(solicitudService.aprobar(id, observacion));
    }

    // PUT /api/solicitudes/{id}/rechazar → 200 (solo ADMIN)
    @PutMapping("/{id}/rechazar")
    public ResponseEntity<SolicitudResponse> rechazar(
            @PathVariable Long id,
            @RequestParam String observacion) {
        return ResponseEntity.ok(solicitudService.rechazar(id, observacion));
    }
}
