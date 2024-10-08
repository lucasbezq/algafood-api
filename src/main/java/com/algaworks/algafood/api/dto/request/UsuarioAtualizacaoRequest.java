package com.algaworks.algafood.api.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UsuarioAtualizacaoRequest {

    @NotBlank
    private String nome;

    @Email
    @NotBlank
    private String email;
}
