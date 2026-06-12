package com.tuproyecto.mensaje;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class MensajeService {
    private final MensajeRepository mensajeRepository;

    public MensajeService(MensajeRepository mensajeRepository) {
        this.mensajeRepository = mensajeRepository;
    }

    public Mensaje enviar(MensajeRequest request, String emisorUsername) {
        Mensaje m = new Mensaje();
        m.setEmisorUsername(emisorUsername);
        m.setReceptorUsername(request.getDestinatarioUsername());
        m.setAsunto(request.getAsunto());
        m.setContenido(request.getContenido());
        return mensajeRepository.save(m);
    }

    public List<Mensaje> getBandejaEntrada(String username) {
        return mensajeRepository.findByReceptorUsername(username);
    }

    public List<Mensaje> getEnviados(String username) {
        return mensajeRepository.findByEmisorUsername(username);
    }

    public Mensaje marcarLeido(Long id, String username) {
        Mensaje m = mensajeRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mensaje no encontrado"));
        if (!m.getReceptorUsername().equals(username))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No puedes marcar mensajes de otros usuarios");
        m.setLeido(true);
        return mensajeRepository.save(m);
    }

    public long contarNoLeidos(String username) {
        return mensajeRepository.countByReceptorUsernameAndLeidoFalse(username);
    }
}
