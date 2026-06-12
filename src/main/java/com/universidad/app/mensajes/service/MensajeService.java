package com.universidad.app.mensajes.service;

import com.universidad.app.mensajes.dto.EnviarMensajeRequest;
import com.universidad.app.mensajes.dto.MensajeResponse;
import com.universidad.app.mensajes.entity.Mensaje;
import com.universidad.app.mensajes.repository.MensajeRepository;
import com.universidad.app.security.Usuario;
import com.universidad.app.security.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MensajeService {

    private final MensajeRepository mensajeRepository;
    private final UsuarioRepository usuarioRepository;

    public MensajeResponse enviar(String emisorUsername, EnviarMensajeRequest request) {
        Usuario emisor = usuarioRepository.findByUsername(emisorUsername)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Emisor no encontrado"));

        Usuario receptor = usuarioRepository.findByUsername(request.getDestinatarioUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Destinatario no encontrado: " + request.getDestinatarioUsername()));

        Mensaje mensaje = Mensaje.builder()
                .emisor(emisor)
                .receptor(receptor)
                .asunto(request.getAsunto())
                .contenido(request.getContenido())
                .build();

        return toResponse(mensajeRepository.save(mensaje));
    }

    public List<MensajeResponse> getBandejaEntrada(String username) {
        Usuario usuario = getUsuario(username);
        return mensajeRepository.findByReceptorOrderByFechaEnvioDesc(usuario)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<MensajeResponse> getEnviados(String username) {
        Usuario usuario = getUsuario(username);
        return mensajeRepository.findByEmisorOrderByFechaEnvioDesc(usuario)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    public MensajeResponse marcarLeido(Long id, String username) {
        Mensaje mensaje = mensajeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mensaje no encontrado"));

        if (!mensaje.getReceptor().getUsername().equals(username)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No puedes marcar mensajes de otros usuarios");
        }

        mensaje.setLeido(true);
        return toResponse(mensajeRepository.save(mensaje));
    }

    public long contarNoLeidos(String username) {
        Usuario usuario = getUsuario(username);
        return mensajeRepository.countByReceptorAndLeidoFalse(usuario);
    }

    private Usuario getUsuario(String username) {
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
    }

    private MensajeResponse toResponse(Mensaje m) {
        return MensajeResponse.builder()
                .id(m.getId())
                .emisor(m.getEmisor().getUsername())
                .receptor(m.getReceptor().getUsername())
                .asunto(m.getAsunto())
                .contenido(m.getContenido())
                .leido(m.isLeido())
                .fechaEnvio(m.getFechaEnvio())
                .build();
    }
}
