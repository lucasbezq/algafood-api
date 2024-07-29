package com.algaworks.algafood;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CadastroCozinhaIntegrationTest {

    @Autowired
    private CadastroCozinhaService service;

    @Test
    public void testarCadastroCozinhaComSucesso() {
        var expectedCozinha = new Cozinha();
        expectedCozinha.setNome("Chinesa");

        var novaCozinha = service.salvar(expectedCozinha);

        assertThat(novaCozinha).isNotNull();
        assertThat(novaCozinha.getId()).isNotNull();
    }

    @Test(expected = ConstraintViolationException.class)
    public void deveFalharAoCadastrarCozinhaSemNome() {
        var expectedCozinha = new Cozinha();

        service.salvar(expectedCozinha);
    }


}
