package com.algaworks.algafood.api.converter;

import com.algaworks.algafood.api.dto.request.PedidoRequest;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PedidoConverter {

    @Autowired
    private ModelMapper modelMapper;

    public Pedido toDomain(PedidoRequest pedidoRequest) {
        return modelMapper.map(pedidoRequest, Pedido.class);
    }

    public void copyToDomain(PedidoRequest pedidoRequest, Pedido pedido) {
        modelMapper.map(pedidoRequest, pedido);
    }

}
