package com.comercio.API_Comercio.mapper;

import com.comercio.API_Comercio.dto.request.ProductoRequestDTO;
import com.comercio.API_Comercio.dto.response.ProductoResponseDTO;
import com.comercio.API_Comercio.entity.Categoria;
import com.comercio.API_Comercio.entity.Producto;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {

    // Recibe el DTO y la categoría ya buscada por el Service
    public Producto toEntity(ProductoRequestDTO dto, Categoria categoria) {
        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        producto.setCategoria(categoria); // Armamos la relación aquí
        return producto;
    }

    public ProductoResponseDTO toResponse(Producto entity) {
        ProductoResponseDTO dto = new ProductoResponseDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setPrecio(entity.getPrecio());
        dto.setStock(entity.getStock());
        dto.setActivo(entity.getActivo());
        dto.setCreadoEn(entity.getCreadoEn());
        
        // Evitamos errores si por alguna razón la categoría es nula
        if (entity.getCategoria() != null) {
            dto.setCategoriaNombre(entity.getCategoria().getNombre());
        }
        
        return dto;
    }
}
