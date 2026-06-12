package com.universidad.app.solicitudes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.universidad.app.solicitudes.entity.Solicitud;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class SolicitudSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // ── Prueba 1: POST sin autenticación → 401 o 403 ──
    @Test
    void crearSolicitud_sinAutenticacion_retorna401o403() throws Exception {
        String body = objectMapper.writeValueAsString(
                Map.of("tipo", "SOPORTE", "descripcion", "Test sin auth")
        );

        mockMvc.perform(post("/api/solicitudes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                        .with(csrf()))
                .andExpect(status().is(
                        org.hamcrest.Matchers.anyOf(
                                org.hamcrest.Matchers.is(401),
                                org.hamcrest.Matchers.is(403)
                        )
                ));
    }

    // ── Prueba 2: POST con usuario autenticado (sin rol especial) → 201 ──
    @Test
    @WithMockUser(username = "usuario1", roles = {"USER"})
    void crearSolicitud_conUsuarioAutenticado_retorna201() throws Exception {
        String body = objectMapper.writeValueAsString(
                Map.of("tipo", "SOPORTE", "descripcion", "Necesito ayuda con el sistema")
        );

        mockMvc.perform(post("/api/solicitudes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                        .with(csrf()))
                .andExpect(status().isCreated());
    }

    // ── Prueba 3: PUT /aprobar con rol USER (sin ADMIN) → 403 ──
    @Test
    @WithMockUser(username = "usuario1", roles = {"USER"})
    void aprobarSolicitud_conRolUser_retorna403() throws Exception {
        // El ID 9999 no existe, pero la verificación de rol ocurre antes
        mockMvc.perform(put("/api/solicitudes/9999/aprobar")
                        .param("observacion", "Aprobado")
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }
}
