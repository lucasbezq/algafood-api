package com.algaworks.algafood.api.converter;

import com.algaworks.algafood.api.dto.FormaPagamentoDTO;
import com.algaworks.algafood.api.dto.request.FormaPagamentoRequest;
import com.algaworks.algafood.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormaPagamentoConverter {

    @Autowired
    private ModelMapper modelMapper;

    public FormaPagamento toDomain(FormaPagamentoRequest formaPagamentoRequest) {
        return modelMapper.map(formaPagamentoRequest, FormaPagamento.class);
    }

    public void copyToDomain(FormaPagamentoRequest formaPagamentoRequest, FormaPagamento formaPagamento) {
        modelMapper.map(formaPagamentoRequest, formaPagamento);
    }

}
