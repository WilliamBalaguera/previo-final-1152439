package com.tuproyecto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SolicitudSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    // Prueba 1: POST sin autenticación → 401/403
    @Test
    void postSolicitud_sinAutenticacion_retorna401o403() throws Exception {
        mockMvc.perform(post("/api/solicitudes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"tipo\":\"SOPORTE\",\"descripcion\":\"Test\"}"))
               .andExpect(status().isUnauthorized());
    }

    // Prueba 2: POST con usuario autenticado (sin rol especial) → 201
    @Test
    @WithMockUser(username = "usuario1", roles = {"USER"})
    void postSolicitud_conUsuarioAutenticado_retorna201() throws Exception {
        mockMvc.perform(post("/api/solicitudes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"tipo\":\"SOPORTE\",\"descripcion\":\"Necesito soporte tecnico\"}"))
               .andExpect(status().isCreated());
    }

    // Prueba 3: PUT aprobar con rol USER (sin ADMIN) → 403
    @Test
    @WithMockUser(username = "usuario1", roles = {"USER"})
    void aprobarSolicitud_conRolUser_retorna403() throws Exception {
        mockMvc.perform(put("/api/solicitudes/999/aprobar")
                .param("observacion", "test"))
               .andExpect(status().isForbidden());
    }
}
