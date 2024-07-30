package com.algaworks.algafood;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CadastroCozinhaIT {

    @LocalServerPort
    private int port;

    @Before
    public void setup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/cozinhas";
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
    public void deveRetornarConter4Cozinhas_QuandoConsultarCozinhas() {
        RestAssured.given()
                    .accept(ContentType.JSON)
                .when()
                    .get()
                .then()
                    .body("", hasSize(4));
    }
}
