package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.CozinhasXMLWarapper;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
    public Cozinha buscar(@PathVariable("cozinhaId") Long cozinhaId) {
        return cozinhaRepository.buscar(cozinhaId);
    }

}
