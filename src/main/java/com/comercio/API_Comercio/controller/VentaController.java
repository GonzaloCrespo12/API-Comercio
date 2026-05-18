package com.comercio.API_Comercio.controller;

import com.comercio.API_Comercio.dto.request.VentaRequestDTO;
import com.comercio.API_Comercio.dto.response.VentaResponseDTO;
import com.comercio.API_Comercio.service.VentaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    // GET /api/ventas
    @GetMapping
    public ResponseEntity<List<VentaResponseDTO>> obtenerTodas() {
        return ResponseEntity.ok(ventaService.obtenerTodas());
    }

    // POST /api/ventas -> Inicia una venta vacía en estado PENDIENTE
    @PostMapping
    public ResponseEntity<VentaResponseDTO> crear(@Valid @RequestBody VentaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ventaService.crear(dto));
    }

    // PUT /api/ventas/{id}/completar -> Cierra la venta (Regla de inmutabilidad)
    @PutMapping("/{id}/completar")
    public ResponseEntity<VentaResponseDTO> completarVenta(@PathVariable Long id) {
        return ResponseEntity.ok(ventaService.completarVenta(id));
    }

    // DELETE /api/ventas/{id} -> Borra si no está COMPLETADA
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        ventaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}