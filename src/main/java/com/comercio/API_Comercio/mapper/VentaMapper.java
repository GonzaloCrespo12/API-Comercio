// Archivo: src/main/java/com/comercio/API_Comercio/mapper/VentaMapper.java
package com.comercio.API_Comercio.mapper;

import com.comercio.API_Comercio.dto.request.VentaRequestDTO;
import com.comercio.API_Comercio.dto.response.VentaResponseDTO;
import com.comercio.API_Comercio.entity.Usuario;
import com.comercio.API_Comercio.entity.Venta;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class VentaMapper {
    public Venta toEntity(VentaRequestDTO dto, Usuario usuario) {
        Venta venta = new Venta();
        venta.setUsuario(usuario);
        venta.setTotal(BigDecimal.ZERO); // Arranca en 0 hasta que le agreguemos detalles
        venta.setEstado("PENDIENTE"); // Por defecto
        return venta;
    }

    public VentaResponseDTO toResponse(Venta entity) {
        VentaResponseDTO dto = new VentaResponseDTO();
        dto.setId(entity.getId());
        dto.setFecha(entity.getFecha());
        dto.setTotal(entity.getTotal());
        dto.setEstado(entity.getEstado());
        if (entity.getUsuario() != null) {
            dto.setUsuarioNombre(entity.getUsuario().getUsername());
        }
        return dto;
    }
}