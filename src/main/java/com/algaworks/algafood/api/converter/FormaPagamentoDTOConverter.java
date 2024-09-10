package com.algaworks.algafood.api.converter;

import com.algaworks.algafood.api.dto.FormaPagamentoDTO;
import com.algaworks.algafood.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FormaPagamentoDTOConverter {

    @Autowired
    private ModelMapper modelMapper;

    public FormaPagamentoDTO toDTO(FormaPagamento formaPagamento) {
        return modelMapper.map(formaPagamento, FormaPagamentoDTO.class);
    }

    public List<FormaPagamentoDTO> toCollectionDTO(List<FormaPagamento> formasPagamento) {
        return formasPagamento.stream()
                .map(formaPagamento -> toDTO(formaPagamento))
                .collect(Collectors.toList());
    }
}
