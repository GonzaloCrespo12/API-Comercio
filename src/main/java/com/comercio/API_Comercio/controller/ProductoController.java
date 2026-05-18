package com.comercio.API_Comercio.controller;

import com.comercio.API_Comercio.dto.request.ProductoRequestDTO;
import com.comercio.API_Comercio.dto.response.ProductoResponseDTO;
import com.comercio.API_Comercio.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    // GET /api/productos?activo=true
    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> obtenerProductos(
            @RequestParam(required = false, defaultValue = "true") Boolean activo) {
        
        // Si nos mandan activo=true, devolvemos solo esos. (Podríamos expandirlo luego si mandan false)
        if (activo) {
            return ResponseEntity.ok(productoService.obtenerActivos());
        }
        return ResponseEntity.ok(List.of()); // Devuelve lista vacía por ahora si no buscan activos
    }

    // GET /api/productos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.obtenerPorId(id));
    }

    // POST /api/productos
    @PostMapping
    public ResponseEntity<ProductoResponseDTO> crear(@Valid @RequestBody ProductoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.crear(dto));
    }

    // PUT /api/productos/{id}
    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody ProductoRequestDTO dto) {
        return ResponseEntity.ok(productoService.actualizar(id, dto));
    }

    // DELETE /api/productos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build(); // 204 No Content, buena práctica para DELETE
    }
}