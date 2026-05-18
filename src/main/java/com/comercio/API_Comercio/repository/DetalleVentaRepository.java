package com.comercio.API_Comercio.repository;

import com.comercio.API_Comercio.entity.DetalleVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {
    // Obtiene todos los ítems que se vendieron en una factura/venta particular
    List<DetalleVenta> findByVentaId(Long ventaId);
}