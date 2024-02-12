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
    private final String token = "";
    private final Faker faker = new Faker();

    @Test
    void testBuscandoTodosOsClientesComSucessoCodigo200(){
        given()
                .header("Authorization",
                        "Bearer " + token)
                .when().get("?pagina=0&tamanho=20&order=reversed")
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
                .when().delete("/22")
                .then()
                .statusCode(204);
    }
}