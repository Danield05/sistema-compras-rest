package com.sistema.compras.repository;

import com.sistema.compras.entity.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {

    // Buscar compras por comercio
    List<Compra> findByComercioId(Long comercioId);

    // Buscar compras por comercio y fecha
    List<Compra> findByComercioIdAndFechaBetween(Long comercioId, LocalDateTime fechaInicio, LocalDateTime fechaFin);

    // Buscar compras por comercio y medio de pago
    List<Compra> findByComercioIdAndMedioPago(Long comercioId, String medioPago);

    // Buscar compras por comercio, fecha y medio de pago
    List<Compra> findByComercioIdAndFechaBetweenAndMedioPago(Long comercioId, LocalDateTime fechaInicio, LocalDateTime fechaFin, String medioPago);

    // Consulta personalizada para obtener compras con información del comercio
    @Query("SELECT c FROM Compra c JOIN FETCH c.comercio WHERE c.comercio.id = :comercioId")
    List<Compra> findComprasWithComercioByComercioId(@Param("comercioId") Long comercioId);
}