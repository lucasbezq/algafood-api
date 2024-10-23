package com.algaworks.algafood.api.dto;

import com.algaworks.algafood.domain.model.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class PedidoDTO {

    private Long id;
    private BigDecimal subTotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    private String status;
    private OffsetDateTime dataCriacao;
    private OffsetDateTime dataConfirmacao;
    private OffsetDateTime dataEntrega;
    private OffsetDateTime dataCancelamento;

    private RestauranteResumoDTO restaurante;
    private UsuarioDTO cliente;
    private EnderecoDTO enderecoEntrega;
    private FormaPagamentoDTO formaPagamento;
    private List<ItemPedidoDTO> itens;

}
