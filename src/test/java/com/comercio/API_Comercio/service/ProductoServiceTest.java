// Archivo: src/test/java/com/comercio/API_Comercio/service/ProductoServiceTest.java
package com.comercio.API_Comercio.service;

import com.comercio.API_Comercio.entity.Producto;
import com.comercio.API_Comercio.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    @Test
    public void testEliminar_BajaLogica() {
        // Arrange (Preparar)
        Long id = 1L;
        Producto producto = new Producto();
        producto.setId(id);
        producto.setActivo(true); // Arranca activo

        when(productoRepository.findById(id)).thenReturn(Optional.of(producto));
        // Simulamos que al intentar borrar fisicamente, MySQL lanza un error de integridad (ya tiene ventas)
        doThrow(new DataIntegrityViolationException("Error FK")).when(productoRepository).delete(producto);

        // Act (Ejecutar)
        productoService.eliminar(id);

        // Assert (Verificar que se cumplio la Regla de Negocio 3)
        assertFalse(producto.getActivo()); // Debe estar en false
        verify(productoRepository).save(producto); // Verificamos que se llamo al metodo save para actualizarlo
    }
}