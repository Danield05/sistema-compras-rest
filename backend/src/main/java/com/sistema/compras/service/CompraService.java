package com.sistema.compras.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistema.compras.entity.Comercio;
import com.sistema.compras.entity.Compra;
import com.sistema.compras.entity.CompraDTO;
import com.sistema.compras.repository.CompraRepository;

@Service
public class CompraService {

    @Autowired
    private CompraRepository compraRepository;

    public List<Compra> findAll() {
        return compraRepository.findAll();
    }

    public List<CompraDTO> findAllDTOs() {
        return compraRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<Compra> findById(Long id) {
        return compraRepository.findById(id);
    }

    public Compra save(Compra compra) {
        return compraRepository.save(compra);
    }

    public void deleteById(Long id) {
        compraRepository.deleteById(id);
    }

    public List<Compra> findByComercioId(Long comercioId) {
        return compraRepository.findByComercioId(comercioId);
    }

    public List<Compra> findByComercioIdAndFechaBetween(Long comercioId, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return compraRepository.findByComercioIdAndFechaBetween(comercioId, fechaInicio, fechaFin);
    }

    public List<Compra> findByComercioIdAndMedioPago(Long comercioId, String medioPago) {
        return compraRepository.findByComercioIdAndMedioPago(comercioId, medioPago);
    }

    public List<Compra> findByComercioIdAndFechaBetweenAndMedioPago(Long comercioId, LocalDateTime fechaInicio, LocalDateTime fechaFin, String medioPago) {
        return compraRepository.findByComercioIdAndFechaBetweenAndMedioPago(comercioId, fechaInicio, fechaFin, medioPago);
    }

    private CompraDTO convertToDTO(Compra compra) {
        return new CompraDTO(
                compra.getId(),
                compra.getFecha(),
                compra.getMedioPago(),
                compra.getComprador(),
                compra.getMontoTotal(),
                compra.getComercio().getId(),
                compra.getComercio().getNombre()
        );
    }

    public CompraDTO saveFromDTO(CompraDTO compraDTO) {
        Compra compra = convertFromDTO(compraDTO);
        Compra savedCompra = save(compra);
        return convertToDTO(savedCompra);
    }

    public CompraDTO updateFromDTO(Long id, CompraDTO compraDTO) {
        Optional<Compra> existingCompra = findById(id);
        if (existingCompra.isPresent()) {
            Compra compra = existingCompra.get();
            compra.setFecha(compraDTO.getFecha());
            compra.setMedioPago(compraDTO.getMedioPago());
            compra.setComprador(compraDTO.getComprador());
            compra.setMontoTotal(compraDTO.getMontoTotal());
            // El comercio no se cambia en update, se mantiene el existente
            Compra updatedCompra = save(compra);
            return convertToDTO(updatedCompra);
        }
        return null;
    }

    private Compra convertFromDTO(CompraDTO compraDTO) {
        Compra compra = new Compra();
        compra.setFecha(compraDTO.getFecha());
        compra.setMedioPago(compraDTO.getMedioPago());
        compra.setComprador(compraDTO.getComprador());
        compra.setMontoTotal(compraDTO.getMontoTotal());
        // Necesitamos obtener el comercio por ID
        Comercio comercio = new Comercio();
        comercio.setId(compraDTO.getComercioId());
        compra.setComercio(comercio);
        return compra;
    }
}