package com.comercio.API_Comercio.repository;

import com.comercio.API_Comercio.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Long> {
    // Query personalizada: Obtiene todo el historial de ventas de un usuario específico
    List<Venta> findByUsuarioId(Long usuarioId);
}