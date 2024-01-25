package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.CozinhasXMLWarapper;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;

@RestController
@RequestMapping(value = "/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @GetMapping
    public List<Cozinha> listar() {
        return cozinhaRepository.listar();
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public CozinhasXMLWarapper listarXML() {
        return new CozinhasXMLWarapper(cozinhaRepository.listar());
    }

    @GetMapping(path = "/{cozinhaId}")
    public ResponseEntity<Cozinha> buscar(@PathVariable("cozinhaId") Long cozinhaId) {
        var cozinha = cozinhaRepository.buscar(cozinhaId);

        if (cozinha == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(cozinha);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cozinha adicionar(@RequestBody Cozinha cozinha) {
        return cozinhaRepository.salvar(cozinha);
    }

    @PutMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> atualizar(@PathVariable Long cozinhaId, @RequestBody Cozinha cozinha) {
        var cozinhaAtual = cozinhaRepository.buscar(cozinhaId);

        if (cozinhaAtual == null) {
            return ResponseEntity.notFound().build();
        }

        BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
        cozinhaAtual = cozinhaRepository.salvar(cozinhaAtual);
        return ResponseEntity.ok(cozinhaAtual);
    }
}
