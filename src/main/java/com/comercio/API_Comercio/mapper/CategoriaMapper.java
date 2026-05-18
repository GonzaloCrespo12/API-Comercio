package com.comercio.API_Comercio.mapper;

import com.comercio.API_Comercio.dto.request.CategoriaRequestDTO;
import com.comercio.API_Comercio.dto.response.CategoriaResponseDTO;
import com.comercio.API_Comercio.entity.Categoria;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {

    // Convierte lo que entra por la API (JSON) a una Entidad para la Base de Datos
    public Categoria toEntity(CategoriaRequestDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());
        return categoria;
    }

    // Convierte lo que sale de la Base de Datos a un JSON para mostrar
    public CategoriaResponseDTO toResponse(Categoria entity) {
        CategoriaResponseDTO dto = new CategoriaResponseDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setDescripcion(entity.getDescripcion());
        return dto;
    }
}
