// Archivo: src/test/java/com/comercio/API_Comercio/controller/DetalleVentaControllerTest.java
package com.comercio.API_Comercio.controller;

import com.comercio.API_Comercio.dto.request.DetalleVentaRequestDTO;
import com.comercio.API_Comercio.dto.response.DetalleVentaResponseDTO;
import com.comercio.API_Comercio.security.CustomUserDetailsService;
import com.comercio.API_Comercio.security.JwtUtil;
import com.comercio.API_Comercio.service.DetalleVentaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DetalleVentaController.class)
@AutoConfigureMockMvc(addFilters = false)
public class DetalleVentaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private DetalleVentaService detalleVentaService;

    @MockitoBean
    private JwtUtil jwtUtil;
    
    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    public void testAgregarDetalle() throws Exception {
        // Arrange
        DetalleVentaRequestDTO request = new DetalleVentaRequestDTO();
        request.setProductoId(1L);
        request.setVentaId(1L);
        request.setCantidad(2);

        DetalleVentaResponseDTO response = new DetalleVentaResponseDTO();
        response.setId(1L);
        response.setSubtotal(new BigDecimal("2000.00"));

        when(detalleVentaService.agregarDetalle(any(DetalleVentaRequestDTO.class))).thenReturn(response);

        // Act & Assert (ObjectMapper para convertir el objeto a JSON)
        mockMvc.perform(post("/api/detalles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated()); // 201 Created
    }
}