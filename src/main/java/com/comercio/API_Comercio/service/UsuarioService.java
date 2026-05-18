package com.comercio.API_Comercio.service;

import com.comercio.API_Comercio.dto.request.UsuarioRequestDTO;
import com.comercio.API_Comercio.dto.response.UsuarioResponseDTO;
import com.comercio.API_Comercio.entity.Usuario;
import com.comercio.API_Comercio.mapper.UsuarioMapper;
import com.comercio.API_Comercio.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
// Archivo: src/main/java/com/comercio/API_Comercio/service/UsuarioService.java
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> obtenerTodos() {
        return usuarioRepository.findAll().stream()
                .map(usuarioMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public UsuarioResponseDTO crear(UsuarioRequestDTO dto) {
        if (usuarioRepository.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("El nombre de usuario ya está en uso");
        }
        Usuario usuario = usuarioMapper.toEntity(dto);
        
        // Encriptamos la contraseña antes de guardarla en la base de datos
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        
        return usuarioMapper.toResponse(usuarioRepository.save(usuario));
    }
}