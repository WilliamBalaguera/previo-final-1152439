package com.tuproyecto.mensaje;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/mensajes")
public class MensajeController {
    private final MensajeService mensajeService;

    public MensajeController(MensajeService mensajeService) {
        this.mensajeService = mensajeService;
    }

    @PostMapping
    public ResponseEntity<Mensaje> enviar(@Valid @RequestBody MensajeRequest request, Principal principal) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mensajeService.enviar(request, principal.getName()));
    }

    @GetMapping("/bandeja-entrada")
    public ResponseEntity<List<Mensaje>> getBandejaEntrada(Principal principal) {
        return ResponseEntity.ok(mensajeService.getBandejaEntrada(principal.getName()));
    }

    @GetMapping("/enviados")
    public ResponseEntity<List<Mensaje>> getEnviados(Principal principal) {
        return ResponseEntity.ok(mensajeService.getEnviados(principal.getName()));
    }

    @PutMapping("/{id}/leer")
    public ResponseEntity<Mensaje> marcarLeido(@PathVariable Long id, Principal principal) {
        return ResponseEntity.ok(mensajeService.marcarLeido(id, principal.getName()));
    }

    @GetMapping("/no-leidos/count")
    public ResponseEntity<Map<String, Long>> contarNoLeidos(Principal principal) {
        return ResponseEntity.ok(Map.of("count", mensajeService.contarNoLeidos(principal.getName())));
    }
}
