package com.universidad.app.mensajes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.universidad.app.mensajes.controller.MensajeController;
import com.universidad.app.mensajes.service.MensajeService;
import com.universidad.app.security.CustomUserDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MensajeController.class)
class MensajeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MensajeService mensajeService;

    // Necesario para que el contexto de seguridad no falle al cargar
    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    // ── Prueba 1: bandeja de entrada con usuario autenticado → 200 ──
    @Test
    @WithMockUser(username = "usuario1")
    void getBandejaEntrada_conUsuarioAutenticado_retorna200() throws Exception {
        when(mensajeService.getBandejaEntrada(anyString())).thenReturn(List.of());

        mockMvc.perform(get("/api/mensajes/bandeja-entrada"))
                .andExpect(status().isOk());
    }

    // ── Prueba 2: bandeja de entrada sin autenticación → 401 o 403 ──
    @Test
    void getBandejaEntrada_sinAutenticacion_retorna401o403() throws Exception {
        mockMvc.perform(get("/api/mensajes/bandeja-entrada"))
                .andExpect(status().is(
                        org.hamcrest.Matchers.anyOf(
                                org.hamcrest.Matchers.is(401),
                                org.hamcrest.Matchers.is(403)
                        )
                ));
    }

    // ── Prueba 3: POST con cuerpo vacío → 400 ──
    @Test
    @WithMockUser(username = "usuario1")
    void enviarMensaje_conCuerpoVacio_retorna400() throws Exception {
        mockMvc.perform(post("/api/mensajes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }
}
