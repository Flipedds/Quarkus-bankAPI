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

    private final String token = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJaXzg2UlhKV0ptMmlkVW5rMUg4WkVXQU1zNm4tZ3c5ZTFoVzk2X25OOVFrIn0.eyJleHAiOjE3MDc4MzE4OTIsImlhdCI6MTcwNzgzMTU5MiwianRpIjoiNzAyZGYyODktZTA5Ni00OTI3LTkyMzktNTY1ZDI3ODEzOTcxIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MTgwL3JlYWxtcy9xdWFya3VzIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6IjY3Zjc5YzVjLTY5ODEtNGUyMi1iNTliLWQwOGY4MWMzNDQwOCIsInR5cCI6IkJlYXJlciIsImF6cCI6ImJhY2tlbmQtc2VydmljZSIsInNlc3Npb25fc3RhdGUiOiJhYmQ1OWZlMy0zNDIxLTQwYTktYTAyOC1iZTQzM2JiNDRhYmEiLCJhY3IiOiIxIiwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm1hbmFnZXIiLCJvZmZsaW5lX2FjY2VzcyIsImRlZmF1bHQtcm9sZXMtcXVhcmt1cyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJlbWFpbCBwcm9maWxlIiwic2lkIjoiYWJkNTlmZTMtMzQyMS00MGE5LWEwMjgtYmU0MzNiYjQ0YWJhIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJmaWxpcGUifQ.alrBNwwCLBBO7qBdGJBcTP9vlVqTllApUCdWLMyXXbcSq1HsYqwclAxK1RlTXrcVP_Q7e-wTQf6qa3fY9ddZLr20SamOA9a6DmrPJ69GPycR05rA6b-pYYj_zjcA0iagi-UgMD9MV4GA83tfm98oHpxWru4AcwsJysWYqW-gSV0pUXeW4MuewfYDHiF0Y1c1Tjfcj80VnHE3ckQUWHsdJ-DEP3GWkGJivI5Tx47PovfZDGut8avqItahTsXu_XHDC-LrhPR8Q-kMYcCTx_bu3pf8nSiUe-qgbdNoonuBVm1VDrNdpEtJQ6vffCQCBkOzxpM33R6xbaG-pxT9A2_rCQ";

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
