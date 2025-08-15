package com.algaworks.algafood.domain.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class VendaDiaria {

    @ApiModelProperty(value = "Data da venda", example = "2023-10-01T00:00:00Z")
    private Date data;

    @ApiModelProperty(value = "Número total de vendas no dia", example = "10")
    private Long totalVendas;

    @ApiModelProperty(value = "Valor total faturado no dia", example = "150.00")
    private BigDecimal totalFaturado;

}
