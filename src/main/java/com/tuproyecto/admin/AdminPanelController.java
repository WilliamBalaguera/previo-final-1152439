package com.tuproyecto.admin;

import com.tuproyecto.solicitud.EstadoSolicitud;
import com.tuproyecto.solicitud.SolicitudService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/solicitudes")
@PreAuthorize("hasRole('ADMIN')")
public class AdminPanelController {
    private final SolicitudService solicitudService;

    public AdminPanelController(SolicitudService solicitudService) {
        this.solicitudService = solicitudService;
    }

    @GetMapping("/panel")
    public String panel(Model model) {
        model.addAttribute("solicitudes", solicitudService.getTodas());
        model.addAttribute("total", solicitudService.contarTotal());
        model.addAttribute("pendientes", solicitudService.contarPorEstado(EstadoSolicitud.PENDIENTE));
        model.addAttribute("aprobadas", solicitudService.contarPorEstado(EstadoSolicitud.APROBADA));
        model.addAttribute("rechazadas", solicitudService.contarPorEstado(EstadoSolicitud.RECHAZADA));
        return "admin/panel";
    }
}
