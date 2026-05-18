package com.comercio.API_Comercio.service;

import com.comercio.API_Comercio.dto.request.ProductoRequestDTO;
import com.comercio.API_Comercio.dto.response.ProductoResponseDTO;
import com.comercio.API_Comercio.entity.Categoria;
import com.comercio.API_Comercio.entity.Producto;
import com.comercio.API_Comercio.mapper.ProductoMapper;
import com.comercio.API_Comercio.repository.CategoriaRepository;
import com.comercio.API_Comercio.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProductoMapper productoMapper;

    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> obtenerActivos() {
        return productoRepository.findByActivo(true).stream()
                .map(productoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductoResponseDTO obtenerPorId(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return productoMapper.toResponse(producto);
    }

    @Transactional
    public ProductoResponseDTO crear(ProductoRequestDTO dto) {
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no existe"));

        Producto nuevoProducto = productoMapper.toEntity(dto, categoria);
        return productoMapper.toResponse(productoRepository.save(nuevoProducto));
    }

    @Transactional
    public ProductoResponseDTO actualizar(Long id, ProductoRequestDTO dto) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no existe"));

        producto.setNombre(dto.getNombre());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        producto.setCategoria(categoria);

        return productoMapper.toResponse(productoRepository.save(producto));
    }

    @Transactional
    public void eliminar(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        try {
            productoRepository.delete(producto);
            productoRepository.flush(); // Forzamos a MySQL a ejecutar el borrado ahora mismo
        } catch (DataIntegrityViolationException e) {
            // Si hay una violación de integridad, desactivamos el producto en lugar de eliminarlo
            producto.setActivo(false);
            productoRepository.save(producto);
        }
    }
}