package com.comercio.API_Comercio.repository;

import com.comercio.API_Comercio.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    // Query personalizada: Busca una categoría por su nombre exacto
    Optional<Categoria> findByNombre(String nombre);
}