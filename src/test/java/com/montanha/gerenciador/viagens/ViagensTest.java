package com.montanha.gerenciador.viagens;

import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.*;

public class ViagensTest {

    @Test
    public void testDadosUmAdministradorQuandoCadastroViajensEntaoObtenhoStatatu200() {
        //Configurar o caminho comun de acesso a minha API Rest
        baseURI = "http://localhost";
        port = 8089;
        basePath = "/api";

        // Login na API Rest com administrador
        String token = given()
                .body("{\n" +
                        "  \"email\": \"admin@email.com\",\n" +
                        "  \"senha\": \"654321\"\n" +
                        "}")
                .contentType(ContentType.JSON)
                .when()
                    .post("/v1/auth")
                .then()
                     .extract()
                     .path("data.token");

        //Cadastrar a viagens
        given()
                .header("Authorization", token)
                .body("{\n" +
                        "  \"acompanhante\": \"Luis H\",\n" +
                        "  \"dataPartida\": \"2024-07-04\",\n" +
                        "  \"dataRetorno\": \"2024-08-04\",\n" +
                        "  \"localDeDestino\": \"Manaus\",\n" +
                        "  \"regiao\": \"Norte\"\n" +
                        "}")
                .contentType(ContentType.JSON)
                .when()
                    .post("/v1/viagens")
                .then()
                    .log().all()
                    .assertThat()
                        .statusCode(201);

    }

}
