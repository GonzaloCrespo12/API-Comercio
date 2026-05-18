// Archivo: src/test/java/com/comercio/API_Comercio/controller/ProductoControllerTest.java
package com.comercio.API_Comercio.controller;

import com.comercio.API_Comercio.dto.response.ProductoResponseDTO;
import com.comercio.API_Comercio.security.CustomUserDetailsService;
import com.comercio.API_Comercio.security.JwtUtil;
import com.comercio.API_Comercio.service.ProductoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductoController.class)
@AutoConfigureMockMvc(addFilters = false) // sin seguridad para testear solo el controller
public class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductoService productoService;

    @MockitoBean
    private JwtUtil jwtUtil;
    
    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    public void testObtenerPorId() throws Exception {
        // Arrange
        ProductoResponseDTO dto = new ProductoResponseDTO();
        dto.setId(1L);
        dto.setNombre("Laptop");
        
        when(productoService.obtenerPorId(1L)).thenReturn(dto);

        // Act & Assert
        mockMvc.perform(get("/api/productos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Laptop"));
    }
}