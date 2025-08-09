package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.converter.CozinhaConverter;
import com.algaworks.algafood.api.converter.CozinhaDTOConverter;
import com.algaworks.algafood.api.dto.CozinhaDTO;
import com.algaworks.algafood.api.dto.request.CozinhaRequest;
import com.algaworks.algafood.api.openapi.controller.CozinhaControllerOpenApi;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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

    @GetMapping
    public Page<CozinhaDTO> listar(Pageable pageable) {
        var cozinhas = cozinhaRepository.findAll(pageable);
        var cozinhasDTO = cozinhaDTOConverter.toCollectionDTO(cozinhas.getContent());
        var cozinhasDTOPage = new PageImpl<>(cozinhasDTO, pageable, cozinhas.getTotalElements());

        return cozinhasDTOPage;
    }

    @GetMapping(path = "/{cozinhaId}")
    public CozinhaDTO buscar(@PathVariable("cozinhaId") Long cozinhaId) {
        var cozinha = cadastroCozinhaService.buscarCozinha(cozinhaId);
        return cozinhaDTOConverter.toDTO(cozinha);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaDTO adicionar(@RequestBody @Valid CozinhaRequest cozinhaRequest) {
        var cozinha = cozinhaConverter.toDomain(cozinhaRequest);
        return cozinhaDTOConverter.toDTO(cadastroCozinhaService.salvar(cozinha));
    }

    @PutMapping("/{cozinhaId}")
    public CozinhaDTO atualizar(@PathVariable Long cozinhaId, @RequestBody @Valid CozinhaRequest cozinhaRequest) {
        var cozinhaAtual = cadastroCozinhaService.buscarCozinha(cozinhaId);
        cozinhaConverter.copyToDomain(cozinhaRequest, cozinhaAtual);

        return cozinhaDTOConverter.toDTO(cadastroCozinhaService.salvar(cozinhaAtual));
    }

    @DeleteMapping("/{cozinhaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cozinhaId) {
        cadastroCozinhaService.excluir(cozinhaId);
    }
}
