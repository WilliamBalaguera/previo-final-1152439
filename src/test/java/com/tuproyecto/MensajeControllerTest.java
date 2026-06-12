package com.tuproyecto;

import com.tuproyecto.mensaje.MensajeController;
import com.tuproyecto.mensaje.MensajeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MensajeController.class)
public class MensajeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MensajeService mensajeService;

    // Prueba 1: GET bandeja-entrada con usuario autenticado → 200
    @Test
    @WithMockUser(username = "usuario1")
    void getBandejaEntrada_conUsuarioAutenticado_retorna200() throws Exception {
        when(mensajeService.getBandejaEntrada("usuario1")).thenReturn(List.of());
        mockMvc.perform(get("/api/mensajes/bandeja-entrada"))
               .andExpect(status().isOk());
    }

    // Prueba 2: GET bandeja-entrada sin autenticación → 401/403
    @Test
    void getBandejaEntrada_sinAutenticacion_retorna401o403() throws Exception {
        mockMvc.perform(get("/api/mensajes/bandeja-entrada"))
               .andExpect(status().isUnauthorized());
    }

    // Prueba 3: POST /api/mensajes con cuerpo vacío → 400
    @Test
    @WithMockUser(username = "usuario1")
    void postMensaje_conCuerpoVacio_retorna400() throws Exception {
        mockMvc.perform(post("/api/mensajes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
               .andExpect(status().isBadRequest());
    }
}
