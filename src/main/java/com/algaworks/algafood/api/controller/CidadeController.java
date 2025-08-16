package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.openapi.controller.CidadeControllerOpenApi;
import com.algaworks.algafood.api.converter.CidadeConverter;
import com.algaworks.algafood.api.converter.CidadeDTOConverter;
import com.algaworks.algafood.api.dto.CidadeDTO;
import com.algaworks.algafood.api.dto.request.CidadeRequest;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;
import com.algaworks.algafood.util.UriHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenApi {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroCidadeService cadastroCidadeService;

    @Autowired
    private CidadeDTOConverter cidadeDTOConverter;

    @Autowired
    private CidadeConverter cidadeConverter;

    @GetMapping
    public List<CidadeDTO> listar() {
        var cidades = cidadeRepository.findAll();
        return cidadeDTOConverter.toCollectionDTO(cidades);
    }

    @GetMapping(path = "/{cidadeId}")
    public CidadeDTO buscar(@PathVariable Long cidadeId) {
        var cidade = cadastroCidadeService.buscarCidade(cidadeId);
        return cidadeDTOConverter.toDTO(cidade);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeDTO adicionar(@RequestBody @Valid CidadeRequest cidadeRequest) {
        try {
            var cidade = cidadeConverter.toDomain(cidadeRequest);
            var cidadeDTO = cidadeDTOConverter.toDTO(cadastroCidadeService.salvar(cidade));

            UriHelper.addUriInResponseHeader(cidadeDTO.getId());

            return cidadeDTO;
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping(path = "/{cidadeId}")
    public CidadeDTO atualizar(@PathVariable Long cidadeId,
                               @RequestBody @Valid CidadeRequest cidadeRequest) {
        var cidadeAtual = cadastroCidadeService.buscarCidade(cidadeId);
        cidadeConverter.copyToDomain(cidadeRequest, cidadeAtual);
        try {
            return cidadeDTOConverter.toDTO(cadastroCidadeService.salvar(cidadeAtual));
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }


    @DeleteMapping(path = "/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cidadeId) {
        cadastroCidadeService.excluir(cidadeId);
    }
}
