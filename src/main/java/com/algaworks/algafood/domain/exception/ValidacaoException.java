package com.algaworks.algafood.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.BindingResult;

@AllArgsConstructor
@Getter
public class ValidacaoException extends RuntimeException {

    private static final long serialVersionUID = -84822742832804993L;

    private BindingResult bindingResult;

}
