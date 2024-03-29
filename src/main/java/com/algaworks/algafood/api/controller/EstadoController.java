package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CadastroEstadoService cadastroEstadoService;

    @GetMapping
    public List<Estado> listar() {
        return estadoRepository.findAll();
    }

    @GetMapping(path = "/{estadoId}")
    public ResponseEntity<Estado> buscar(@PathVariable Long estadoId) {
        var estado = estadoRepository.findById(estadoId);

        if (estado.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(estado.get());
    }

    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody Estado estado) {
        var novoEstado = cadastroEstadoService.salvar(estado);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoEstado);
    }

    @PutMapping(path = "/{estadoId}")
    public ResponseEntity<Estado> salvar(@PathVariable Long estadoId, @RequestBody Estado estado) {
        var estadoAtual = estadoRepository.findById(estadoId);

        if (estadoAtual.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        BeanUtils.copyProperties(estado, estadoAtual.get(), "id");
        var estadoSalvo = cadastroEstadoService.salvar(estadoAtual.get());
        return ResponseEntity.ok(estadoSalvo);
    }

    @DeleteMapping(path = "/{estadoId}")
    public ResponseEntity<?> remover(@PathVariable Long estadoId) {
        try {
            cadastroEstadoService.excluir(estadoId);
            return ResponseEntity.noContent().build();

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        } catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

}
