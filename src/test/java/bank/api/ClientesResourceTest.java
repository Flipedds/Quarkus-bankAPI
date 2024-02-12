package bank.api;

import bank.api.application.ClientesResource;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestHTTPEndpoint(ClientesResource.class)
class ClientesResourceTest {
    private final String token = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJaXzg2UlhKV0ptMmlkVW5rMUg4WkVXQU1zNm4tZ3c5ZTFoVzk2X25OOVFrIn0.eyJleHAiOjE3MDc3Njc3MDEsImlhdCI6MTcwNzc2NzQwMSwianRpIjoiYjc1MzBlYzMtNzllYS00OWQ5LWIwZGMtYTk5Mzc1NjA3ZmMwIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MTgwL3JlYWxtcy9xdWFya3VzIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6IjY3Zjc5YzVjLTY5ODEtNGUyMi1iNTliLWQwOGY4MWMzNDQwOCIsInR5cCI6IkJlYXJlciIsImF6cCI6ImJhY2tlbmQtc2VydmljZSIsInNlc3Npb25fc3RhdGUiOiJmZTk0MjY0My0xNGY4LTQxMDItOGI2Ni1mZTAwOTdlYjU4NGUiLCJhY3IiOiIxIiwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm1hbmFnZXIiLCJvZmZsaW5lX2FjY2VzcyIsImRlZmF1bHQtcm9sZXMtcXVhcmt1cyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJlbWFpbCBwcm9maWxlIiwic2lkIjoiZmU5NDI2NDMtMTRmOC00MTAyLThiNjYtZmUwMDk3ZWI1ODRlIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJmaWxpcGUifQ.ecvYlG7xHzPzQbQ3DrSQpN74-tx78o8QPXj2asiqhUQbIfoZBvyMdyTfdcrpWnZc_WmRhWAZYtSJF_R6aWW1VtmJG5Hf3NmflAijuGPZlcA1wp4UXa_zpSUufHr-xOKZydiu3cD09LDOD_uaUyuBIZ4fWrhoYFO7FohXZnqpsDV0e-uUGG-8B7WflgYarpWnMxwR0YlMeUjjd2K_WC9RJSysUH7wvl1ieaO_9bbAGlvWbJS9nyJXyfGKA1p_F6VN_XINcsR9p5yylteROE6wEwZ6EtUOFAD4TIGnzNvbPBtc9Lgu550Qe6HCYQdES0fn0-NWjn_GqI_Tuu3XV87N_A";
    private final Faker faker = new Faker();

    @Test
    void testBuscandoTodosOsClientesComSucessoCodigo200(){
        given()
                .header("Authorization",
                        "Bearer " + token)
                .when().get()
                .then()
                .statusCode(200);
    }

    @Test
    void testBuscandoClienteComSucessoCodigo200() {
        given()
                .header("Authorization",
                        "Bearer " + token)
          .when().get("/1")
          .then()
             .statusCode(200);
    }

    @Test
    void testBuscandoClienteQueNaoExisteCodigo404() {
        given()
                .header("Authorization",
                        "Bearer " + token)
                .when().get("/100")
                .then()
                .statusCode(404);
    }

    @Test
    void testCriandoClienteComSucessoCodigo201(){
        given()
                .header("Authorization",
                        "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(String.format("""
                        {
                        "nome": "%s",
                        "cpf": "%s",
                        "genero": "%s",
                        "telefone": "9948495833",
                        "email": "%s",
                        "endereco": {
                            "logradouro": "rua 1",
                            "bairro": "bairro",
                            "cep": "12345678",
                            "cidade": "SÃO PAULO",
                            "uf": "SP",
                            "numero": 200,
                            "complemento": ""
                            }
                        }""",
                        faker.name().fullName(),
                        faker.cpf().valid(false),
                        faker.gender().binaryTypes(),
                        faker.internet().emailAddress()))
                .when().post()
                .then()
                .statusCode(201);
    }

    @Test
    void testErroAoCriarClienteCampoNomeFaltanteCodigo400(){
        given()
                .header("Authorization",
                        "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(String.format("""
                        {
                        "cpf": "%s",
                        "genero": "%s",
                        "telefone": "9948495833",
                        "email": "%s",
                        "endereco": {
                            "logradouro": "rua 1",
                            "bairro": "bairro",
                            "cep": "12345678",
                            "cidade": "SÃO PAULO",
                            "uf": "SP",
                            "numero": 200,
                            "complemento": ""
                            }
                        }""",
                        faker.cpf().valid(false),
                        faker.gender().binaryTypes(),
                        faker.internet().emailAddress()))
                .when().post()
                .then()
                .statusCode(400);
    }
    @Test
    void testAtualizandoClienteComSucessoCodigo200() {
        given()
                .header("Authorization",
                        "Bearer " + token)
                .contentType(ContentType.JSON)
                .body("""

                        {
                      "id": 1,
                      "endereco": {
                          "bairro": "arco leste"
                          }
                      }
                      """)
                .when().put()
                .then()
                .statusCode(200);
    }

    @Test
    void testAtualizandoClienteQueNaoExisteCodigo404() {
        given()
                .header("Authorization",
                        "Bearer " + token)
                .contentType(ContentType.JSON)
                .body("""

                        {
                      "id": 1000,
                      "endereco": {
                          "bairro": "arco leste"
                          }
                      }
                      """)
                .when().put()
                .then()
                .statusCode(404);
    }

    @Test
    void testDeletandoClienteQueNaoExisteCodigo404() {
        given()
                .header("Authorization",
                        "Bearer " + token)
                .when().delete("/1000")
                .then()
                .statusCode(404);
    }

    @Test
    void testDeletandoClienteComSucessoCodigo204() {
        given()
                .header("Authorization",
                        "Bearer " + token)
                .when().delete("/17")
                .then()
                .statusCode(204);
    }
}