package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.converter.EstadoConverter;
import com.algaworks.algafood.api.converter.EstadoDTOConverter;
import com.algaworks.algafood.api.dto.EstadoDTO;
import com.algaworks.algafood.api.dto.request.EstadoRequest;
import com.algaworks.algafood.api.openapi.controller.EstadoControllerOpenApi;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/estados")
public class EstadoController implements EstadoControllerOpenApi {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CadastroEstadoService cadastroEstadoService;

    @Autowired
    private EstadoDTOConverter estadoDTOConverter;

    @Autowired
    private EstadoConverter estadoConverter;

    @GetMapping
    public CollectionModel<EstadoDTO> listar() {
        var estados = estadoRepository.findAll();
        return estadoDTOConverter.toCollectionModel(estados);
    }

    @GetMapping(path = "/{estadoId}")
    public EstadoDTO buscar(@PathVariable Long estadoId) {
        return estadoDTOConverter.toModel(cadastroEstadoService.buscarEstado(estadoId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoDTO adicionar(@RequestBody @Valid EstadoRequest estadoRequest) {
        var estado = estadoConverter.toDomain(estadoRequest);
        return estadoDTOConverter.toModel(cadastroEstadoService.salvar(estado));
    }

    @PutMapping(path = "/{estadoId}")
    public EstadoDTO ataualizar(@PathVariable Long estadoId, @RequestBody @Valid EstadoRequest estadoRequest) {
        var estadoAtual = cadastroEstadoService.buscarEstado(estadoId);
        estadoConverter.copyToDomain(estadoRequest, estadoAtual);
        return estadoDTOConverter.toModel(cadastroEstadoService.salvar(estadoAtual));
    }

    @DeleteMapping(path = "/{estadoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long estadoId) {
        cadastroEstadoService.excluir(estadoId);
    }
}
