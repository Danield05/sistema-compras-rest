package com.sistema.compras.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.compras.entity.Compra;
import com.sistema.compras.entity.CompraDTO;
import com.sistema.compras.service.CompraService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/compras")
@CrossOrigin(origins = "http://localhost:4200")
public class CompraController {

    @Autowired
    private CompraService compraService;

    // Obtener todas las compras
    @GetMapping
    public ResponseEntity<List<CompraDTO>> getAllCompras() {
        List<CompraDTO> compras = compraService.findAllDTOs();
        return ResponseEntity.ok(compras);
    }

    // Obtener una compra por ID
    @GetMapping("/{id}")
    public ResponseEntity<Compra> getCompraById(@PathVariable Long id) {
        Optional<Compra> compra = compraService.findById(id);
        return compra.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

    // Crear una nueva compra
    @PostMapping
    public ResponseEntity<CompraDTO> createCompra(@Valid @RequestBody CompraDTO compraDTO) {
        CompraDTO savedCompra = compraService.saveFromDTO(compraDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCompra);
    }

    // Actualizar una compra existente
    @PutMapping("/{id}")
    public ResponseEntity<CompraDTO> updateCompra(@PathVariable Long id, @Valid @RequestBody CompraDTO compraDTO) {
        CompraDTO updatedCompra = compraService.updateFromDTO(id, compraDTO);
        if (updatedCompra != null) {
            return ResponseEntity.ok(updatedCompra);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar una compra
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompra(@PathVariable Long id) {
        if (compraService.findById(id).isPresent()) {
            compraService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Obtener compras por comercio
    @GetMapping("/comercio/{comercioId}")
    public ResponseEntity<List<Compra>> getComprasByComercio(@PathVariable Long comercioId) {
        List<Compra> compras = compraService.findByComercioId(comercioId);
        return ResponseEntity.ok(compras);
    }

    // Obtener compras por comercio y rango de fechas
    @GetMapping("/comercio/{comercioId}/fecha")
    public ResponseEntity<List<Compra>> getComprasByComercioAndFecha(
            @PathVariable Long comercioId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        List<Compra> compras = compraService.findByComercioIdAndFechaBetween(comercioId, fechaInicio, fechaFin);
        return ResponseEntity.ok(compras);
    }

    // Obtener compras por comercio y medio de pago
    @GetMapping("/comercio/{comercioId}/medio-pago/{medioPago}")
    public ResponseEntity<List<Compra>> getComprasByComercioAndMedioPago(
            @PathVariable Long comercioId,
            @PathVariable String medioPago) {
        List<Compra> compras = compraService.findByComercioIdAndMedioPago(comercioId, medioPago);
        return ResponseEntity.ok(compras);
    }

    // Obtener compras por comercio, rango de fechas y medio de pago
    @GetMapping("/comercio/{comercioId}/filtrar")
    public ResponseEntity<List<Compra>> getComprasByComercioFechaAndMedioPago(
            @PathVariable Long comercioId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin,
            @RequestParam(required = false) String medioPago) {

        List<Compra> compras;

        if (fechaInicio != null && fechaFin != null && medioPago != null) {
            compras = compraService.findByComercioIdAndFechaBetweenAndMedioPago(comercioId, fechaInicio, fechaFin, medioPago);
        } else if (fechaInicio != null && fechaFin != null) {
            compras = compraService.findByComercioIdAndFechaBetween(comercioId, fechaInicio, fechaFin);
        } else if (medioPago != null) {
            compras = compraService.findByComercioIdAndMedioPago(comercioId, medioPago);
        } else {
            compras = compraService.findByComercioId(comercioId);
        }

        return ResponseEntity.ok(compras);
    }
}