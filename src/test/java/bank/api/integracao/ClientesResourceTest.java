package bank.api.integracao;

import bank.api.application.cliente.resources.ClientesResource;
import bank.api.domain.cliente.entities.Cliente;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import net.datafaker.Faker;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import static io.restassured.RestAssured.given;

@QuarkusTest
@TestSecurity(user = "test", roles = {"manager"})
@TestHTTPEndpoint(ClientesResource.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ClientesResourceTest {
    private static Long id;
    private final Faker faker = new Faker();

    @Test
    @Order(1)
    void testBuscandoTodosOsClientesComSucessoCodigo200(){
        given()
                .when().get("?pagina=0&tamanho=20&order=reversed")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(2)
    void testBuscandoClienteComSucessoCodigo200() {
        given()
          .when().get("/1")
          .then()
             .statusCode(200);
    }

    @Test
    @Order(3)
    void testBuscandoClienteQueNaoExisteCodigo404() {
        given()
                .when().get("/100")
                .then()
                .statusCode(404);
    }

    @Test
    @Order(4)
    void testCriandoClienteComSucessoCodigo201(){
        Cliente cliente = given()
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
                .statusCode(201)
                .extract().as(Cliente.class);

        id = cliente.getId();
    }

    @Test
    @Order(5)
    void testErroAoCriarClienteCampoNomeFaltanteCodigo400(){
        given()
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
    @Order(6)
    void testAtualizandoClienteComSucessoCodigo200() {
        given()
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
    @Order(7)
    void testAtualizandoClienteQueNaoExisteCodigo404() {
        given()
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
    @Order(8)
    void testDeletandoClienteQueNaoExisteCodigo404() {
        given()
                .when().delete("/1000")
                .then()
                .statusCode(404);
    }

    @Test
    @Order(9)
    void testDeletandoClienteComSucessoCodigo204() {
        given()
                .when().delete("/"+ id)
                .then()
                .statusCode(204);
    }
}