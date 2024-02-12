package bank.api;

import bank.api.application.ClientesResource;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestHTTPEndpoint(ClientesResource.class)
class ClientesResourceTest {
    private final String token = "";
    @Test
    void testGetClienteByIdEndpoint() {
        given()
                .header("Authorization",
                        "Bearer " + token)
          .when().get("/1")
          .then()
             .statusCode(200);
    }

    @Test
    void testGetClienteByIdIfNotExistEndpoint() {
        given()
                .header("Authorization",
                        "Bearer " + token)
                .when().get("/11")
                .then()
                .statusCode(404);
    }
}