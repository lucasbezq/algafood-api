package com.algaworks.algafood;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import com.algaworks.algafood.domain.util.Constants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CadastroCozinhaIT {

    @Autowired
    private CadastroCozinhaService service;

    @Test
    public void deveAtribuirId_QuandoCadastrasCozinhaComDadosCorrestos() {
        var expectedCozinha = new Cozinha();
        expectedCozinha.setNome("Chinesa");

        var novaCozinha = service.salvar(expectedCozinha);

        assertThat(novaCozinha).isNotNull();
        assertThat(novaCozinha.getId()).isNotNull();
    }

    @Test(expected = ConstraintViolationException.class)
    public void deveFalhar_QuandoCadastrarCozinhaSemNome() {
        var expectedCozinha = new Cozinha();

        service.salvar(expectedCozinha);
    }

    @Test
    public void deveFalhar_QuandoExcluirCozinhaEmUso() {
        try {
            service.excluir(1L);
        } catch (EntidadeEmUsoException e) {
            assertThat(e.getMessage().equals(Constants.MSG_COZINHA_EM_USO));
        }
    }

    @Test(expected = CozinhaNaoEncontradaException.class)
    public void deveFalhar_QuandoExcluirCozinhaInexistente() {
        service.excluir(1000L);
    }

}
