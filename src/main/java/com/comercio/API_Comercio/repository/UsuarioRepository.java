package com.comercio.API_Comercio.repository;

import com.comercio.API_Comercio.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Query personalizada: Busca un usuario por su nombre de usuario
    Optional<Usuario> findByUsername(String username);
    
    // Extra muy útil: verifica si un usuario ya existe antes de registrarlo
    boolean existsByUsername(String username);
}