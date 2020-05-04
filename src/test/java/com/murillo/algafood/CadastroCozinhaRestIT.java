package com.murillo.algafood;

import com.murillo.algafood.domain.model.Cozinha;
import com.murillo.algafood.domain.repository.CozinhaRepository;
import com.murillo.algafood.util.DatabaseCleaner;
import com.murillo.algafood.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroCozinhaRestIT {

    public static final int ID_COZINHA_INEXISTENTE = 999;
    @LocalServerPort
    private int port;

//    @Autowired
//    private Flyway flyway;

    @Value("${cozinha.test.names}")
    private String cozinhaTestNames;

    private String jsonCorretoCozinhaArgentina;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @BeforeEach
    public void setup() {

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/cozinhas";

        //flyway.migrate();

        databaseCleaner.clearTables();
        prepararDados();

        jsonCorretoCozinhaArgentina = ResourceUtils.getContentFromResource("/json/correto/cozinha_argentina.json");

    }

    private void prepararDados() {

        String[] nomesCozinhas = getNomesCozinhasParaTeste();

        for (int i = 0; i < nomesCozinhas.length; i++) {
            Cozinha novaCozinha = new Cozinha();
            novaCozinha.setNome(nomesCozinhas[i]);
            cozinhaRepository.save(novaCozinha);
        }

    }


    @Test
    public void verRetornarStatus200_QuandoConsultarCozinhas() {

        RestAssured.given()
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value());

    }

    @Test
    public void deveConterNCozinhas_QuandoConsultarCozinhas() {

        RestAssured.given()
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .body("nome", Matchers.hasSize(getNomesCozinhasParaTeste().length))
                .body("nome", Matchers.hasItems(cozinhaTestNames.split(",")));
    }

    private String[] getNomesCozinhasParaTeste() {
        return cozinhaTestNames.split(",");
    }

    @Test
    public void deveRetornarStatus201_QuandoCadastrarNovaCozinhaValida() {

        RestAssured.given()
                .body(jsonCorretoCozinhaArgentina)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value());

    }


    @Test
    public void deveRetornarRespostaEStatusCorretos_QuandoConsultarCozinhaExistente() {

        RestAssured.given()
                .pathParam("cozinhaId", 2)
                .accept(ContentType.JSON)
                .when()
                .get("/{cozinhaId}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("nome", Matchers.equalTo(getNomesCozinhasParaTeste()[1]));

    }

    @Test
    public void deveRetornarRespostaEStatus404_QuandoConsultarCozinhaInexistente() {

        RestAssured.given()
                .pathParam("cozinhaId", ID_COZINHA_INEXISTENTE)
                .accept(ContentType.JSON)
                .when()
                .get("/{cozinhaId}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());

    }


}
