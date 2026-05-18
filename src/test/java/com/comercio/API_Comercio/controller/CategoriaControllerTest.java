// Archivo: src/test/java/com/comercio/API_Comercio/controller/CategoriaControllerTest.java
package com.comercio.API_Comercio.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.comercio.API_Comercio.dto.response.CategoriaResponseDTO;
import com.comercio.API_Comercio.security.CustomUserDetailsService;
import com.comercio.API_Comercio.security.JwtUtil;
import com.comercio.API_Comercio.service.CategoriaService;

@WebMvcTest(CategoriaController.class)
@AutoConfigureMockMvc(addFilters = false) // Sin filtro JWT solo para este test
public class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CategoriaService categoriaService;

    // Necesitamos mockear las dependencias de seguridad para que levante el contexto
    @MockitoBean
    private JwtUtil jwtUtil;
    
    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    public void testObtenerTodas() throws Exception {
        // Arrange
        CategoriaResponseDTO dto = new CategoriaResponseDTO();
        dto.setId(1);
        dto.setNombre("Muebles");
        
        when(categoriaService.obtenerTodas()).thenReturn(List.of(dto));

        // Act & Assert (Se combinan gracias a MockMvc)
        mockMvc.perform(get("/api/categorias"))
                .andExpect(status().isOk()) // Esperamos un HTTP 200 OK
                .andExpect(jsonPath("$[0].nombre").value("Muebles")); // Esperamos que el JSON tenga "Muebles"
    }
}