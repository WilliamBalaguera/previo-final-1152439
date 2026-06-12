package com.tuproyecto.solicitud;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudController {
    private final SolicitudService solicitudService;

    public SolicitudController(SolicitudService solicitudService) {
        this.solicitudService = solicitudService;
    }

    @PostMapping
    public ResponseEntity<Solicitud> radicar(@Valid @RequestBody SolicitudRequest request, Principal principal) {
        return ResponseEntity.status(HttpStatus.CREATED).body(solicitudService.radicar(request, principal.getName()));
    }

    @GetMapping("/mis-solicitudes")
    public ResponseEntity<List<Solicitud>> getMisSolicitudes(Principal principal) {
        return ResponseEntity.ok(solicitudService.getMisSolicitudes(principal.getName()));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Solicitud>> getTodas() {
        return ResponseEntity.ok(solicitudService.getTodas());
    }

    @PutMapping("/{id}/aprobar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Solicitud> aprobar(@PathVariable Long id, @RequestParam String observacion) {
        return ResponseEntity.ok(solicitudService.aprobar(id, observacion));
    }

    @PutMapping("/{id}/rechazar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Solicitud> rechazar(@PathVariable Long id, @RequestParam String observacion) {
        return ResponseEntity.ok(solicitudService.rechazar(id, observacion));
    }
}
