package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.util.ResourceUtil;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import com.algaworks.algafood.util.DatabaseCleaner;

import java.util.List;

import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroCozinhaIT {

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    private List<Cozinha> cozinhas;

    private String jsonCadastroCozinhas;

    private final long COZINHA_INEXISTENTE = 1000L;

    private Cozinha cozinhaAmericana;

    @Before
    public void setup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/cozinhas";

        databaseCleaner.clearTables();
        prepararDados();
        cozinhas = cozinhaRepository.findAll();
        jsonCadastroCozinhas = ResourceUtil.getContentFromResource("/json/correto/cozinha-chinesa.json");
    }

    @Test
    public void deveRetornarStatus200_QuandoConsultarCozinhas() {
        RestAssured.given()
                    .accept(ContentType.JSON)
                .when()
                    .get()
                .then()
                    .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deveRetornarOTotalDeCozinhas_QuandoConsultarCozinhas() {
        RestAssured.given()
                    .accept(ContentType.JSON)
                .when()
                    .get()
                .then()
                    .body("", hasSize(cozinhas.size()));
    }

    @Test
    public void deveRetornarStatus201_QuandoCadastrarCozinhas() {
        RestAssured.given()
                    .body(jsonCadastroCozinhas)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .post()
                .then()
                    .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void deveRetornarRespostaEStatus201_QuandoConsultarCozinhaExistente() {
        RestAssured.given()
                    .accept(ContentType.JSON)
                .pathParams("cozinhaId", cozinhaAmericana.getId())
                .when()
                    .get("/{cozinhaId}")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("nome", equalTo(cozinhaAmericana.getNome()));
    }

    @Test
    public void deveRetornarStatus404_QuandoConsultarCozinhaInexistente() {
        RestAssured.given()
                    .accept(ContentType.JSON)
                    .pathParams("cozinhaId", COZINHA_INEXISTENTE)
                .when()
                    .get("/{cozinhaId}")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value());
    }

    private void prepararDados() {
        var cozinha1 = new Cozinha();
        cozinha1.setNome("Tailandesa");
        cozinhaRepository.save(cozinha1);

        cozinhaAmericana = new Cozinha();
        cozinhaAmericana.setNome("Americana");
        cozinhaRepository.save(cozinhaAmericana);
    }
}
