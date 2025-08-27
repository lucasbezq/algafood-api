package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.converter.CozinhaConverter;
import com.algaworks.algafood.api.converter.CozinhaDTOConverter;
import com.algaworks.algafood.api.dto.CozinhaDTO;
import com.algaworks.algafood.api.dto.request.CozinhaRequest;
import com.algaworks.algafood.api.openapi.controller.CozinhaControllerOpenApi;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/cozinhas")
public class CozinhaController implements CozinhaControllerOpenApi {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;

    @Autowired
    private CozinhaDTOConverter cozinhaDTOConverter;

    @Autowired
    private CozinhaConverter cozinhaConverter;

    @Autowired
    private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

    @GetMapping
    public PagedModel<CozinhaDTO> listar(Pageable pageable) {
        var cozinhas = cozinhaRepository.findAll(pageable);
        return pagedResourcesAssembler.toModel(cozinhas, cozinhaDTOConverter);
    }

    @GetMapping(path = "/{cozinhaId}")
    public CozinhaDTO buscar(@PathVariable("cozinhaId") Long cozinhaId) {
        var cozinha = cadastroCozinhaService.buscarCozinha(cozinhaId);
        return cozinhaDTOConverter.toModel(cozinha);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaDTO adicionar(@RequestBody @Valid CozinhaRequest cozinhaRequest) {
        var cozinha = cozinhaConverter.toDomain(cozinhaRequest);
        return cozinhaDTOConverter.toModel(cadastroCozinhaService.salvar(cozinha));
    }

    @PutMapping("/{cozinhaId}")
    public CozinhaDTO atualizar(@PathVariable Long cozinhaId, @RequestBody @Valid CozinhaRequest cozinhaRequest) {
        var cozinhaAtual = cadastroCozinhaService.buscarCozinha(cozinhaId);
        cozinhaConverter.copyToDomain(cozinhaRequest, cozinhaAtual);

        return cozinhaDTOConverter.toModel(cadastroCozinhaService.salvar(cozinhaAtual));
    }

    @DeleteMapping("/{cozinhaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cozinhaId) {
        cadastroCozinhaService.excluir(cozinhaId);
    }
}
