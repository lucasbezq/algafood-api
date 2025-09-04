package com.algaworks.algafood.api.converter;

import com.algaworks.algafood.api.controller.CidadeController;
import com.algaworks.algafood.api.controller.FormaPagamentoController;
import com.algaworks.algafood.api.controller.RestauranteFormaPagamentoController;
import com.algaworks.algafood.api.dto.CidadeDTO;
import com.algaworks.algafood.api.dto.FormaPagamentoDTO;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class FormaPagamentoDTOConverter extends RepresentationModelAssemblerSupport<FormaPagamento, FormaPagamentoDTO> {

    @Autowired
    private ModelMapper modelMapper;

    public FormaPagamentoDTOConverter() {
        super(FormaPagamento.class, FormaPagamentoDTO.class);
    }

    public FormaPagamentoDTO toModel(FormaPagamento formaPagamento) {
        var formaPagamentoDTO = modelMapper.map(formaPagamento, FormaPagamentoDTO.class);

        formaPagamentoDTO.add(linkTo(methodOn(FormaPagamentoController.class)
                .buscar(formaPagamento.getId(), null))
                .withSelfRel());

        formaPagamentoDTO.add(linkTo(methodOn(FormaPagamentoController.class)
                .listar(null))
                .withRel("formas-pagamento"));

        return formaPagamentoDTO;
    }

    @Override
    public CollectionModel<FormaPagamentoDTO> toCollectionModel(Iterable<? extends FormaPagamento> entities) {
        return super.toCollectionModel(entities)
                .add(linkTo(methodOn(FormaPagamentoController.class).listar(null))
                        .withSelfRel());
    }

    public CollectionModel<FormaPagamentoDTO> toCollectionModel(Iterable<? extends FormaPagamento> entities, Long restauranteId) {
        return super.toCollectionModel(entities)
                .add(linkTo(methodOn(RestauranteFormaPagamentoController.class)
                        .listar(restauranteId))
                        .withSelfRel());
    }

}
