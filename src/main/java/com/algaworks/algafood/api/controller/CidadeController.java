package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/{cidades}")
public class CidadeController {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroCidadeService cadastroCidadeService;

    @GetMapping
    public List<Cidade> listar() {
        return cidadeRepository.listar();
    }

    @GetMapping(path = "/{cidadeId}")
    public ResponseEntity<Cidade> buscar(@PathVariable Long cidadeId) {
        var cidade = cidadeRepository.buscar(cidadeId);

        if (cidade == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(cidade);
    }

    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody Cidade cidade) {

        try {
            var novaCidade = cadastroCidadeService.salvar(cidade);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaCidade);

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping(path = "/{cidadeId}")
    public ResponseEntity<?> atualizar(@PathVariable Long cidadeId, @RequestBody Cidade cidade) {
        try {
            var cidadeAtual = cidadeRepository.buscar(cidadeId);

            if (cidadeAtual == null) {
                return ResponseEntity.notFound().build();
            }

            BeanUtils.copyProperties(cidade, cidadeAtual, "id");
            cidadeAtual = cadastroCidadeService.salvar(cidadeAtual);
            return ResponseEntity.ok().body(cidadeAtual);

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
