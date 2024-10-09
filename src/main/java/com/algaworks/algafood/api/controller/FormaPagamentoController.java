package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.converter.FormaPagamentoConverter;
import com.algaworks.algafood.api.converter.FormaPagamentoDTOConverter;
import com.algaworks.algafood.api.dto.FormaPagamentoDTO;
import com.algaworks.algafood.api.dto.request.FormaPagamentoRequest;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.service.CadastroFormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamentoService;

    @Autowired
    private FormaPagamentoDTOConverter formaPagamentoDTOConverter;

    @Autowired
    private FormaPagamentoConverter formaPagamentoConverter;

    @GetMapping
    public List<FormaPagamentoDTO> listar() {
        var formasPagamento = formaPagamentoRepository.findAll();
        return formaPagamentoDTOConverter.toCollectionDTO(formasPagamento);
    }

    @GetMapping("/{formaPagamentoId}")
    public FormaPagamentoDTO buscar(@PathVariable Long formaPagamentoId) {
        var formaPagamento = cadastroFormaPagamentoService.buscarFormaPagamento(formaPagamentoId);
        return formaPagamentoDTOConverter.toDTO(formaPagamento);
    }

    @PostMapping
    public FormaPagamentoDTO adicionar(@RequestBody @Valid FormaPagamentoRequest formaPagamentoRequest) {
        var formaPagamento = formaPagamentoConverter.toDomain(formaPagamentoRequest);
        return formaPagamentoDTOConverter.toDTO(cadastroFormaPagamentoService.salvar(formaPagamento));
    }

    @PutMapping("/{formaPagamentoId}")
    public FormaPagamentoDTO atualizar(@PathVariable Long formaPagamentoId,
                                       @RequestBody @Valid FormaPagamentoRequest formaPagamentoRequest) {
        var formaPagamentoAtual = cadastroFormaPagamentoService.buscarFormaPagamento(formaPagamentoId);
        formaPagamentoConverter.copyToDomain(formaPagamentoRequest, formaPagamentoAtual);
        return formaPagamentoDTOConverter.toDTO(cadastroFormaPagamentoService.salvar(formaPagamentoAtual));
    }

    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void excluir(@PathVariable Long formaPagamentoId) {
        cadastroFormaPagamentoService.excluir(formaPagamentoId);
    }


}
