package com.comercio.API_Comercio.dto.response;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class VentaResponseDTO {
    private Long id;
    private LocalDateTime fecha;
    private BigDecimal total;
    private String estado;
    private String usuarioNombre;
}