package com.comercio.API_Comercio.mapper;

import com.comercio.API_Comercio.dto.request.DetalleVentaRequestDTO;
import com.comercio.API_Comercio.dto.response.DetalleVentaResponseDTO;
import com.comercio.API_Comercio.entity.DetalleVenta;
import com.comercio.API_Comercio.entity.Producto;
import com.comercio.API_Comercio.entity.Venta;
import org.springframework.stereotype.Component;

@Component
public class DetalleVentaMapper {
    public DetalleVenta toEntity(DetalleVentaRequestDTO dto, Producto producto, Venta venta) {
        DetalleVenta detalle = new DetalleVenta();
        detalle.setCantidad(dto.getCantidad());
        detalle.setProducto(producto);
        detalle.setVenta(venta);
        // Nota: Precio y subtotal los inyectaremos en la capa Service con lógica de negocio real
        return detalle;
    }

    public DetalleVentaResponseDTO toResponse(DetalleVenta entity) {
        DetalleVentaResponseDTO dto = new DetalleVentaResponseDTO();
        dto.setId(entity.getId());
        dto.setCantidad(entity.getCantidad());
        dto.setPrecioUnitario(entity.getPrecioUnitario());
        dto.setSubtotal(entity.getSubtotal());
        if (entity.getProducto() != null) {
            dto.setProductoNombre(entity.getProducto().getNombre());
        }
        return dto;
    }
}