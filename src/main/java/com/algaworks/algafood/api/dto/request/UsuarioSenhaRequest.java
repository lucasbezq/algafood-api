package com.algaworks.algafood.api.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UsuarioSenhaRequest {

    @NotBlank
    private String senhaAtual;

    @NotBlank
    private String novaSenha;

}
