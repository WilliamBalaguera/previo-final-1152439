package com.universidad.app.admin;

import com.universidad.app.solicitudes.entity.Solicitud;
import com.universidad.app.solicitudes.service.SolicitudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/solicitudes")
@RequiredArgsConstructor
public class AdminSolicitudController {

    private final SolicitudService solicitudService;

    @GetMapping("/panel")
    public String panel(Model model) {
        model.addAttribute("total",     solicitudService.contarTotal());
        model.addAttribute("pendientes",solicitudService.contarPorEstado(Solicitud.EstadoSolicitud.PENDIENTE));
        model.addAttribute("aprobadas", solicitudService.contarPorEstado(Solicitud.EstadoSolicitud.APROBADA));
        model.addAttribute("rechazadas",solicitudService.contarPorEstado(Solicitud.EstadoSolicitud.RECHAZADA));
        model.addAttribute("solicitudes",solicitudService.getTodas());
        return "admin/panel-solicitudes";
    }
}
