package com.algaworks.algafood.api.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class PedidoRequest {

    @Valid
    @NotNull
    private RestauranteRefRequest restaurante;

    @Valid
    @NotNull
    private FormaPagamentoRefRequest formaPagamento;

    @Valid
    @NotNull
    private EnderecoRequest enderecoEntrega;

    @Valid
    @NotNull
    @Size(min = 1)
    private List<ItemPedidoRequest> itens;

}
