package com.comercio.API_Comercio.service;

import com.comercio.API_Comercio.dto.request.DetalleVentaRequestDTO;
import com.comercio.API_Comercio.dto.response.DetalleVentaResponseDTO;
import com.comercio.API_Comercio.entity.DetalleVenta;
import com.comercio.API_Comercio.entity.Producto;
import com.comercio.API_Comercio.entity.Venta;
import com.comercio.API_Comercio.mapper.DetalleVentaMapper;
import com.comercio.API_Comercio.repository.DetalleVentaRepository;
import com.comercio.API_Comercio.repository.ProductoRepository;
import com.comercio.API_Comercio.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class DetalleVentaService {

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private DetalleVentaMapper detalleVentaMapper;

    @Transactional
    public DetalleVentaResponseDTO agregarDetalle(DetalleVentaRequestDTO dto) {
        Venta venta = ventaRepository.findById(dto.getVentaId())
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));

        // Protección extra pragmática: no modificar ventas cerradas
        if ("COMPLETADA".equalsIgnoreCase(venta.getEstado())) {
            throw new RuntimeException("La venta ya está completada, no admite más detalles");
        }

        Producto producto = productoRepository.findById(dto.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // REGLA DE NEGOCIO 1: Gestión de Stock
        if (producto.getStock() < dto.getCantidad()) {
            throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre());
        }

        // 1. Descontar stock
        producto.setStock(producto.getStock() - dto.getCantidad());
        productoRepository.save(producto);

        // 2. Armar el detalle
        DetalleVenta detalle = detalleVentaMapper.toEntity(dto, producto, venta);
        
        // 3. Congelar el precio actual y calcular el subtotal (precio * cantidad)
        detalle.setPrecioUnitario(producto.getPrecio());
        BigDecimal subtotal = producto.getPrecio().multiply(new BigDecimal(dto.getCantidad()));
        detalle.setSubtotal(subtotal);

        // 4. Actualizar el total de la Venta "padre"
        venta.setTotal(venta.getTotal().add(subtotal));
        ventaRepository.save(venta);

        // 5. Guardar el detalle final
        return detalleVentaMapper.toResponse(detalleVentaRepository.save(detalle));
    }
}