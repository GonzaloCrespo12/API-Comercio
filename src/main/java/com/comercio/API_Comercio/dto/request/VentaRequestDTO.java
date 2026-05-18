package com.comercio.API_Comercio.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VentaRequestDTO {
    @NotNull(message = "El ID del usuario es obligatorio")
    private Long usuarioId;
}