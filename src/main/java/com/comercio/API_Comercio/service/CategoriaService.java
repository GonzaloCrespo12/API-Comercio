package com.comercio.API_Comercio.service;

import com.comercio.API_Comercio.dto.request.CategoriaRequestDTO;
import com.comercio.API_Comercio.dto.response.CategoriaResponseDTO;
import com.comercio.API_Comercio.entity.Categoria;
import com.comercio.API_Comercio.mapper.CategoriaMapper;
import com.comercio.API_Comercio.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private CategoriaMapper categoriaMapper;

    @Transactional(readOnly = true)
    public List<CategoriaResponseDTO> obtenerTodas() {
        return categoriaRepository.findAll().stream()
                .map(categoriaMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoriaResponseDTO obtenerPorId(Integer id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        return categoriaMapper.toResponse(categoria);
    }

    @Transactional
    public CategoriaResponseDTO crear(CategoriaRequestDTO dto) {
        Categoria nuevaCategoria = categoriaMapper.toEntity(dto);
        Categoria guardada = categoriaRepository.save(nuevaCategoria);
        return categoriaMapper.toResponse(guardada);
    }

    @Transactional
    public CategoriaResponseDTO actualizar(Integer id, CategoriaRequestDTO dto) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        
        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());
        
        return categoriaMapper.toResponse(categoriaRepository.save(categoria));
    }

    @Transactional
    public void eliminar(Integer id) {
        if (!categoriaRepository.existsById(id)) {
            throw new RuntimeException("Categoría no encontrada");
        }
        categoriaRepository.deleteById(id);
    }
}