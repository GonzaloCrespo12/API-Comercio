// Archivo: src/test/java/com/comercio/API_Comercio/service/VentaServiceTest.java
package com.comercio.API_Comercio.service;

import com.comercio.API_Comercio.entity.Venta;
import com.comercio.API_Comercio.repository.VentaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VentaServiceTest {

    @Mock
    private VentaRepository ventaRepository;

    @InjectMocks
    private VentaService ventaService;

    @Test
    public void testEliminarVentaCompletada_LanzaExcepcion() {
        // Arrange
        Long id = 1L;
        Venta venta = new Venta();
        venta.setId(id);
        venta.setEstado("COMPLETADA"); // Estado protegido

        when(ventaRepository.findById(id)).thenReturn(Optional.of(venta));

        // Act & Assert (Verificamos que tire RuntimeException por la Regla 2)
        RuntimeException excepcion = assertThrows(RuntimeException.class, () -> {
            ventaService.eliminar(id);
        });

        assertEquals("No se puede eliminar una venta COMPLETADA", excepcion.getMessage());
    }
}