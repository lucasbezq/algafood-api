package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.converter.FormaPagamentoConverter;
import com.algaworks.algafood.api.converter.FormaPagamentoDTOConverter;
import com.algaworks.algafood.api.dto.FormaPagamentoDTO;
import com.algaworks.algafood.api.dto.request.FormaPagamentoRequest;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.service.CadastroFormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    public ResponseEntity<List<FormaPagamentoDTO>> listar(ServletWebRequest request) {
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

        var eTag = "0";
        var dataUltimaAtualizacao = formaPagamentoRepository.getDataultimaAtualizacao();
        if (dataUltimaAtualizacao != null) eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
        if (request.checkNotModified(eTag)) return null;

        var formasPagamento = formaPagamentoRepository.findAll();
        var dto = formaPagamentoDTOConverter.toCollectionDTO(formasPagamento);
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePrivate())
                .eTag(eTag)
                .body(dto);
    }

    @GetMapping("/{formaPagamentoId}")
    public ResponseEntity<FormaPagamentoDTO> buscar(@PathVariable Long formaPagamentoId) {
        var formaPagamento = cadastroFormaPagamentoService.buscarFormaPagamento(formaPagamentoId);
        var dto = formaPagamentoDTOConverter.toDTO(formaPagamento);
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(dto);
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
