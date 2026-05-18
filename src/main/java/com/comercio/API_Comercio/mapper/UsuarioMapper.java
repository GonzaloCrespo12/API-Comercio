package com.comercio.API_Comercio.mapper;

import com.comercio.API_Comercio.dto.request.UsuarioRequestDTO;
import com.comercio.API_Comercio.dto.response.UsuarioResponseDTO;
import com.comercio.API_Comercio.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {
    public Usuario toEntity(UsuarioRequestDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setUsername(dto.getUsername());
        usuario.setPassword(dto.getPassword()); // Más adelante la encriptaremos
        usuario.setRol(dto.getRol().toUpperCase()); // Normalizamos a mayúsculas
        return usuario;
    }

    public UsuarioResponseDTO toResponse(Usuario entity) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setRol(entity.getRol());
        dto.setFechaRegistro(entity.getFechaRegistro());
        return dto;
    }
}