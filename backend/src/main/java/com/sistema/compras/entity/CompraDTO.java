package com.sistema.compras.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CompraDTO {
    private Long id;
    private LocalDateTime fecha;
    private String medioPago;
    private String comprador;
    private BigDecimal montoTotal;
    private Long comercioId;
    private String comercioNombre;

    public CompraDTO() {}

    public CompraDTO(Long id, LocalDateTime fecha, String medioPago, String comprador, BigDecimal montoTotal, Long comercioId, String comercioNombre) {
        this.id = id;
        this.fecha = fecha;
        this.medioPago = medioPago;
        this.comprador = comprador;
        this.montoTotal = montoTotal;
        this.comercioId = comercioId;
        this.comercioNombre = comercioNombre;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getMedioPago() {
        return medioPago;
    }

    public void setMedioPago(String medioPago) {
        this.medioPago = medioPago;
    }

    public String getComprador() {
        return comprador;
    }

    public void setComprador(String comprador) {
        this.comprador = comprador;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }

    public Long getComercioId() {
        return comercioId;
    }

    public void setComercioId(Long comercioId) {
        this.comercioId = comercioId;
    }

    public String getComercioNombre() {
        return comercioNombre;
    }

    public void setComercioNombre(String comercioNombre) {
        this.comercioNombre = comercioNombre;
    }
}