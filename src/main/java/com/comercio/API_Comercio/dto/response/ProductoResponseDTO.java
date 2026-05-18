package com.comercio.API_Comercio.dto.response;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProductoResponseDTO {
    private Long id;
    private String nombre;
    private BigDecimal precio;
    private Integer stock;
    private Boolean activo;
    private LocalDateTime creadoEn;
    private String categoriaNombre; // el nombre directo para que sea más fácil de leer en el front
}