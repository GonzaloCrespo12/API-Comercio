// Archivo: src/test/java/com/comercio/API_Comercio/controller/AuthControllerTest.java
package com.comercio.API_Comercio.controller;

import com.comercio.API_Comercio.dto.request.UsuarioRequestDTO;
import com.comercio.API_Comercio.dto.response.UsuarioResponseDTO;
import com.comercio.API_Comercio.security.CustomUserDetailsService;
import com.comercio.API_Comercio.security.JwtUtil;
import com.comercio.API_Comercio.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UsuarioService usuarioService;

    @MockitoBean
    private AuthenticationManager authenticationManager;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @MockitoBean
    private JwtUtil jwtUtil;

    @Test
    public void testRegistroUsuario() throws Exception {
        // Arrange
        UsuarioRequestDTO request = new UsuarioRequestDTO();
        request.setUsername("nuevoUser");
        request.setPassword("12345");
        request.setRol("EMPLEADO");

        UsuarioResponseDTO response = new UsuarioResponseDTO();
        response.setUsername("nuevoUser");

        when(usuarioService.crear(any(UsuarioRequestDTO.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/api/auth/registro")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }
}