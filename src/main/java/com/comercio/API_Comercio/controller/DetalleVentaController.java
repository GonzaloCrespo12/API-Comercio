package com.comercio.API_Comercio.controller;

import com.comercio.API_Comercio.dto.request.DetalleVentaRequestDTO;
import com.comercio.API_Comercio.dto.response.DetalleVentaResponseDTO;
import com.comercio.API_Comercio.service.DetalleVentaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/detalles")
public class DetalleVentaController {

    @Autowired
    private DetalleVentaService detalleVentaService;

    // POST /api/detalles -> Agrega un producto a una venta y descuenta stock
    @PostMapping
    public ResponseEntity<DetalleVentaResponseDTO> agregarDetalle(@Valid @RequestBody DetalleVentaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(detalleVentaService.agregarDetalle(dto));
    }
}