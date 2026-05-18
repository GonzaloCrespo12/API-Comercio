package com.comercio.API_Comercio.dto.response;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class DetalleVentaResponseDTO {
    private Long id;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
    private String productoNombre;
}