package com.comercio.API_Comercio.repository;

import com.comercio.API_Comercio.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // Query personalizada: Trae los productos filtrando por si están activos (true/false)
    List<Producto> findByActivo(Boolean activo);
}