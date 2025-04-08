package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.converter.CidadeConverter;
import com.algaworks.algafood.api.converter.CidadeDTOConverter;
import com.algaworks.algafood.api.dto.CidadeDTO;
import com.algaworks.algafood.api.dto.request.CidadeRequest;
import com.algaworks.algafood.api.exceptionHandler.ApiError;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Cidades")
@RestController
@RequestMapping(value = "/cidades")
public class CidadeController {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroCidadeService cadastroCidadeService;

    @Autowired
    private CidadeDTOConverter cidadeDTOConverter;

    @Autowired
    private CidadeConverter cidadeConverter;

    @ApiOperation("Lista as cidades")
    @GetMapping
    public List<CidadeDTO> listar() {
        var cidades = cidadeRepository.findAll();
        return cidadeDTOConverter.toCollectionDTO(cidades);
    }

    @ApiOperation("Busca uma cidade através do ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da cidade inválido.", response = ApiError.class),
            @ApiResponse(code = 404, message = "Cidade não encontrada.", response = ApiError.class)
    })
    @GetMapping(path = "/{cidadeId}")
    public CidadeDTO buscar(@ApiParam(value = "ID de uma cidade", example = "1") @PathVariable Long cidadeId) {
        var cidade = cadastroCidadeService.buscarCidade(cidadeId);
        return cidadeDTOConverter.toDTO(cidade);
    }

    @ApiOperation("Cadastra uma cidade")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeDTO adicionar(@ApiParam(name = "body", value = "Representação de uma nova cidade")
                                   @RequestBody @Valid CidadeRequest cidadeRequest) {
        try {
            var cidade = cidadeConverter.toDomain(cidadeRequest);
            return cidadeDTOConverter.toDTO(cadastroCidadeService.salvar(cidade));
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @ApiOperation("Atualiza uma cidade através do ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cidade atualizada."),
            @ApiResponse(code = 404, message = "Cidade não encontrada.", response = ApiError.class)
    })
    @PutMapping(path = "/{cidadeId}")
    public CidadeDTO atualizar(@ApiParam(value = "ID de uma cidade", example = "1") @PathVariable Long cidadeId,
                               @ApiParam(name = "body", value = "Representação de uma nova cidade com os novos dados")
                               @RequestBody @Valid CidadeRequest cidadeRequest) {
        var cidadeAtual = cadastroCidadeService.buscarCidade(cidadeId);
        cidadeConverter.copyToDomain(cidadeRequest, cidadeAtual);
        try {
            return cidadeDTOConverter.toDTO(cadastroCidadeService.salvar(cidadeAtual));
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @ApiOperation("Exclui uma cidade através do ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cidade excluída."),
            @ApiResponse(code = 404, message = "Cidade não encontrada.", response = ApiError.class)
    })
    @DeleteMapping(path = "/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@ApiParam(value = "ID de uma cidade", example = "1") @PathVariable Long cidadeId) {
        cadastroCidadeService.excluir(cidadeId);
    }
}
