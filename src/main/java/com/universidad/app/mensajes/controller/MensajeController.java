package com.universidad.app.mensajes.controller;

import com.universidad.app.mensajes.dto.EnviarMensajeRequest;
import com.universidad.app.mensajes.dto.MensajeResponse;
import com.universidad.app.mensajes.service.MensajeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/mensajes")
@RequiredArgsConstructor
public class MensajeController {

    private final MensajeService mensajeService;

    // POST /api/mensajes → 201
    @PostMapping
    public ResponseEntity<MensajeResponse> enviarMensaje(
            @Valid @RequestBody EnviarMensajeRequest request,
            Principal principal) {
        MensajeResponse response = mensajeService.enviar(principal.getName(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // GET /api/mensajes/bandeja-entrada → 200
    @GetMapping("/bandeja-entrada")
    public ResponseEntity<List<MensajeResponse>> getBandejaEntrada(Principal principal) {
        return ResponseEntity.ok(mensajeService.getBandejaEntrada(principal.getName()));
    }

    // GET /api/mensajes/enviados → 200
    @GetMapping("/enviados")
    public ResponseEntity<List<MensajeResponse>> getEnviados(Principal principal) {
        return ResponseEntity.ok(mensajeService.getEnviados(principal.getName()));
    }

    // PUT /api/mensajes/{id}/leer → 200 o 404
    @PutMapping("/{id}/leer")
    public ResponseEntity<MensajeResponse> marcarLeido(@PathVariable Long id, Principal principal) {
        return ResponseEntity.ok(mensajeService.marcarLeido(id, principal.getName()));
    }

    // GET /api/mensajes/no-leidos/count → {"count": N}
    @GetMapping("/no-leidos/count")
    public ResponseEntity<Map<String, Long>> contarNoLeidos(Principal principal) {
        long count = mensajeService.contarNoLeidos(principal.getName());
        return ResponseEntity.ok(Map.of("count", count));
    }
}
