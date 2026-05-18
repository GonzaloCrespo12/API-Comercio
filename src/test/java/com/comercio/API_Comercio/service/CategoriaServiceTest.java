// Archivo: src/test/java/com/comercio/API_Comercio/service/CategoriaServiceTest.java
package com.comercio.API_Comercio.service;

import com.comercio.API_Comercio.dto.response.CategoriaResponseDTO;
import com.comercio.API_Comercio.entity.Categoria;
import com.comercio.API_Comercio.mapper.CategoriaMapper;
import com.comercio.API_Comercio.repository.CategoriaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private CategoriaMapper categoriaMapper;

    @InjectMocks
    private CategoriaService categoriaService;

    @Test
    public void testObtenerPorId() {
        // 1. Arrange (Preparar el escenario)
        Integer id = 1;
        Categoria categoria = new Categoria();
        categoria.setId(id);
        categoria.setNombre("Electrónica");

        CategoriaResponseDTO dto = new CategoriaResponseDTO();
        dto.setId(id);
        dto.setNombre("Electrónica");

        // simulamos el comportamiento del repositorio y del mapper
        when(categoriaRepository.findById(id)).thenReturn(Optional.of(categoria));
        when(categoriaMapper.toResponse(categoria)).thenReturn(dto);

        // 2. Act (Actuar / Ejecutar el método real)
        CategoriaResponseDTO resultado = categoriaService.obtenerPorId(id);

        // 3. Assert (Afirmar / Verificar el resultado)
        assertEquals("Electrónica", resultado.getNombre());
        assertEquals(1, resultado.getId());
    }
}