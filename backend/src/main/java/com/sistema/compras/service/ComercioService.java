package com.sistema.compras.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistema.compras.entity.Comercio;
import com.sistema.compras.repository.ComercioRepository;

@Service
public class ComercioService {

    @Autowired
    private ComercioRepository comercioRepository;

    public List<Comercio> findAll() {
        return comercioRepository.findAll();
    }

    public Optional<Comercio> findById(Long id) {
        return comercioRepository.findById(id);
    }

    public Comercio save(Comercio comercio) {
        return comercioRepository.save(comercio);
    }

    public void deleteById(Long id) {
        comercioRepository.deleteById(id);
    }
}