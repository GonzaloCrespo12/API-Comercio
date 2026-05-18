package com.comercio.API_Comercio.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UsuarioResponseDTO {
    private Long id;
    private String username;
    private String rol;
    private LocalDateTime fechaRegistro;
    // ¡OJO! La contraseña NUNCA va en el ResponseDTO por seguridad.
}