package com.sistema.compras.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.compras.entity.Comercio;
import com.sistema.compras.service.ComercioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/comercios")
@CrossOrigin(origins = "http://localhost:4200")
public class ComercioController {

    @Autowired
    private ComercioService comercioService;

    // Obtener todos los comercios
    @GetMapping
    public ResponseEntity<List<Comercio>> getAllComercios() {
        List<Comercio> comercios = comercioService.findAll();
        return ResponseEntity.ok(comercios);
    }

    // Obtener un comercio por ID
    @GetMapping("/{id}")
    public ResponseEntity<Comercio> getComercioById(@PathVariable Long id) {
        Optional<Comercio> comercio = comercioService.findById(id);
        return comercio.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    // Crear un nuevo comercio
    @PostMapping
    public ResponseEntity<Comercio> createComercio(@Valid @RequestBody Comercio comercio) {
        Comercio savedComercio = comercioService.save(comercio);
        return ResponseEntity.ok(savedComercio);
    }

    // Actualizar un comercio existente
    @PutMapping("/{id}")
    public ResponseEntity<Comercio> updateComercio(@PathVariable Long id, @Valid @RequestBody Comercio comercioDetails) {
        Optional<Comercio> existingComercio = comercioService.findById(id);
        if (existingComercio.isPresent()) {
            Comercio comercio = existingComercio.get();
            comercio.setNombre(comercioDetails.getNombre());
            comercio.setLugar(comercioDetails.getLugar());
            Comercio updatedComercio = comercioService.save(comercio);
            return ResponseEntity.ok(updatedComercio);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar un comercio
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComercio(@PathVariable Long id) {
        if (comercioService.findById(id).isPresent()) {
            comercioService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}