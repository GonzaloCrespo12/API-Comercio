package com.comercio.API_Comercio.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoriaRequestDTO {
    
    // @NotBlank asegura que no envíen el nombre vacío ni con puros espacios
    @NotBlank(message = "El nombre de la categoría es obligatorio")
    private String nombre;
    
    private String descripcion;
}
