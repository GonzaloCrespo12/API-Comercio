package com.comercio.API_Comercio.service;

import com.comercio.API_Comercio.dto.request.VentaRequestDTO;
import com.comercio.API_Comercio.dto.response.VentaResponseDTO;
import com.comercio.API_Comercio.entity.Usuario;
import com.comercio.API_Comercio.entity.Venta;
import com.comercio.API_Comercio.mapper.VentaMapper;
import com.comercio.API_Comercio.repository.UsuarioRepository;
import com.comercio.API_Comercio.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private VentaMapper ventaMapper;

    @Transactional(readOnly = true)
    public List<VentaResponseDTO> obtenerTodas() {
        return ventaRepository.findAll().stream()
                .map(ventaMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public VentaResponseDTO crear(VentaRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Venta nuevaVenta = ventaMapper.toEntity(dto, usuario);
        return ventaMapper.toResponse(ventaRepository.save(nuevaVenta));
    }

    @Transactional
    public void eliminar(Long id) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));

        // REGLA DE NEGOCIO 2: Inmutabilidad
        if ("COMPLETADA".equalsIgnoreCase(venta.getEstado())) {
            throw new RuntimeException("No se puede eliminar una venta COMPLETADA");
        }
        
        ventaRepository.deleteById(id);
    }
    
    @Transactional
    public VentaResponseDTO completarVenta(Long id) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));
                
        venta.setEstado("COMPLETADA");
        return ventaMapper.toResponse(ventaRepository.save(venta));
    }
}