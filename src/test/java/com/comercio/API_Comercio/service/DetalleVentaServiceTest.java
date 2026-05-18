// Archivo: src/test/java/com/comercio/API_Comercio/service/DetalleVentaServiceTest.java
package com.comercio.API_Comercio.service;

import com.comercio.API_Comercio.dto.request.DetalleVentaRequestDTO;
import com.comercio.API_Comercio.entity.Producto;
import com.comercio.API_Comercio.entity.Venta;
import com.comercio.API_Comercio.repository.ProductoRepository;
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
public class DetalleVentaServiceTest {

    @Mock
    private VentaRepository ventaRepository;

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private DetalleVentaService detalleVentaService;

    @Test
    public void testAgregarDetalle_SinStock_LanzaExcepcion() {
        // Arrange
        DetalleVentaRequestDTO dto = new DetalleVentaRequestDTO();
        dto.setVentaId(1L);
        dto.setProductoId(1L);
        dto.setCantidad(10); // Quieren comprar 10

        Venta venta = new Venta();
        venta.setId(1L);
        venta.setEstado("PENDIENTE");

        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Monitor");
        producto.setStock(5); // Solo hay 5 en stock

        when(ventaRepository.findById(1L)).thenReturn(Optional.of(venta));
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        // Act & Assert (Regla de negocio 1: Falla si no hay stock)
        RuntimeException excepcion = assertThrows(RuntimeException.class, () -> {
            detalleVentaService.agregarDetalle(dto);
        });

        assertEquals("Stock insuficiente para el producto: Monitor", excepcion.getMessage());
    }
}