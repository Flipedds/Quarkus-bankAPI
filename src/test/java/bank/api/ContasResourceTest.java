package bank.api;

import bank.api.application.ContasResource;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestHTTPEndpoint(ContasResource.class)
public class ContasResourceTest {

    private final String token = "";

    @Test
    void testCriandoContaComSucessoCodigo201() {
        given()
                .header("Authorization",
                        "Bearer " + token)
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "tipoConta": "CORRENTE",
                          "clienteId": 1
                        }
                        """)
                .when().post()
                .then()
                .statusCode(201);
        }

    @Test
    void testErroAoCriarContaClienteNaoExisteCodigo404() {
        given()
                .header("Authorization",
                        "Bearer " + token)
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
    void testBuscandoTodosAsContasAtivasComSucessoCodigo200(){
        given()
                .header("Authorization",
                        "Bearer " + token)
                .when().get()
                .then()
                .statusCode(200);
    }

    @Test
    void testBuscandoContaComSucessoCodigo200() {
        given()
                .header("Authorization",
                        "Bearer " + token)
                .when().get("/1")
                .then()
                .statusCode(200);
    }

    @Test
    void testBuscandoContaQueNaoExisteCodigo404() {
        given()
                .header("Authorization",
                        "Bearer " + token)
                .when().get("/1000")
                .then()
                .statusCode(404);
    }

    @Test
    void testDesativandoContaQueNaoExisteCodigo404() {
        given()
                .header("Authorization",
                        "Bearer " + token)
                .when().delete("/1000")
                .then()
                .statusCode(404);
    }

    @Test
    void testDesativandoContaJaDesativadaCodigo404() {
        given()
                .header("Authorization",
                        "Bearer " + token)
                .when().delete("/2")
                .then()
                .statusCode(404);
    }

    @Test
    void testDesativandoContaComSucessoCodigo204() {
        given()
                .header("Authorization",
                        "Bearer " + token)
                .when().delete("/4")
                .then()
                .statusCode(204);
    }
}
