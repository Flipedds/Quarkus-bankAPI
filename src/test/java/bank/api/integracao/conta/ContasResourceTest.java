package bank.api.integracao.conta;

import bank.api.presentation.resources.ContasResource;
import bank.api.domain.entities.Conta;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import static io.restassured.RestAssured.given;

@QuarkusTest
@TestHTTPEndpoint(ContasResource.class)
@TestSecurity(user = "test", roles = {"manager"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ContasResourceTest {

    private static Long id;

    @Test
    @Order(1)
    void testCriandoContaComSucessoCodigo201() {
        Conta conta = given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "tipoConta": "POUPANCA",
                          "clienteId": 2
                        }
                        """)
                .when().post()
                .then()
                .statusCode(201)
                .extract().as(Conta.class);
            id = conta.getId();
        }

    @Test
    @Order(2)
    void testErroAoCriarContaClienteNaoExisteCodigo404() {
        given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "tipoConta": "CORRENTE",
                          "clienteId": 1000
                        }
                        """)
                .when().post()
                .then()
                .statusCode(404);
    }

    @Test
    @Order(3)
    void testBuscandoTodosAsContasAtivasComSucessoCodigo200(){
        given()
                .when().get("?pagina=0&tamanho=4")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(4)
    void testBuscandoContaComSucessoCodigo200() {
        given()
                .when().get("/1")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(5)
    void testBuscandoContaQueNaoExisteCodigo404() {
        given()
                .when().get("/1000")
                .then()
                .statusCode(404);
    }

    @Test
    @Order(6)
    void testDesativandoContaQueNaoExisteCodigo404() {
        given()
                .when().delete("/1000")
                .then()
                .statusCode(404);
    }

    @Test
    @Order(7)
    void testDesativandoContaJaDesativadaCodigo404() {
        given()
                .when().delete("/3")
                .then()
                .statusCode(404);
    }

    @Test
    @Order(8)
    void testDesativandoContaComSucessoCodigo204() {
        given()
                .when().delete("/"+ id)
                .then()
                .statusCode(204);
    }

    @Test
    @Order(9)
    void testErroAoCriarContaClienteJaPossuiEsteTipoDeContaCodigo409() {
        given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "tipoConta": "CORRENTE",
                          "clienteId": 1
                        }
                        """)
                .when().post()
                .then()
                .statusCode(409);
    }

    @Test
    @Order(10)
    void testDesativandoContaComSaldoCodigo409() {
        given()
                .when().delete("/2")
                .then()
                .statusCode(409);
    }
}
