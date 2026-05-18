// Archivo: src/test/java/com/comercio/API_Comercio/controller/VentaControllerTest.java
package com.comercio.API_Comercio.controller;

import com.comercio.API_Comercio.dto.response.VentaResponseDTO;
import com.comercio.API_Comercio.security.CustomUserDetailsService;
import com.comercio.API_Comercio.security.JwtUtil;
import com.comercio.API_Comercio.service.VentaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VentaController.class)
@AutoConfigureMockMvc(addFilters = false)
public class VentaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VentaService ventaService;

    @MockitoBean
    private JwtUtil jwtUtil;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    public void testObtenerTodas() throws Exception {
        // Arrange
        VentaResponseDTO venta = new VentaResponseDTO();
        venta.setId(1L);
        venta.setEstado("PENDIENTE");

        when(ventaService.obtenerTodas()).thenReturn(List.of(venta));

        // Act & Assert
        mockMvc.perform(get("/api/ventas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].estado").value("PENDIENTE"));
    }
}