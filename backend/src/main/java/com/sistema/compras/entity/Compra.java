package com.sistema.compras.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Compra")
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La fecha de la compra es obligatoria")
    @Column(nullable = false)
    private LocalDateTime fecha;

    @NotBlank(message = "El medio de pago es obligatorio")
    @Pattern(regexp = "Efectivo|Tarjeta|Plazos", message = "El medio de pago debe ser: Efectivo, Tarjeta o Plazos")
    @Column(name = "medio_pago", nullable = false, length = 50)
    private String medioPago;

    @NotBlank(message = "El nombre del comprador es obligatorio")
    @Size(max = 255, message = "El nombre del comprador no puede exceder 255 caracteres")
    @Column(nullable = false, length = 255)
    private String comprador;

    @NotNull(message = "El monto total es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto total debe ser mayor a 0")
    @Digits(integer = 8, fraction = 2, message = "El monto total debe tener máximo 8 dígitos enteros y 2 decimales")
    @Column(name = "monto_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal montoTotal;

    @NotNull(message = "El comercio es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comercio_id", nullable = false)
    private Comercio comercio;

    // Constructores
    public Compra() {}

    public Compra(LocalDateTime fecha, String medioPago, String comprador, BigDecimal montoTotal, Comercio comercio) {
        this.fecha = fecha;
        this.medioPago = medioPago;
        this.comprador = comprador;
        this.montoTotal = montoTotal;
        this.comercio = comercio;
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

    public Comercio getComercio() {
        return comercio;
    }

    public void setComercio(Comercio comercio) {
        this.comercio = comercio;
    }
}