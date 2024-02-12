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
    private final String token = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJaXzg2UlhKV0ptMmlkVW5rMUg4WkVXQU1zNm4tZ3c5ZTFoVzk2X25OOVFrIn0.eyJleHAiOjE3MDc3NzA5NTcsImlhdCI6MTcwNzc3MDY1NywianRpIjoiZWRhMGM0N2EtNTE1Mi00ZGRmLWJiOGUtZTNhMzJlMDMxZTZlIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MTgwL3JlYWxtcy9xdWFya3VzIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6IjY3Zjc5YzVjLTY5ODEtNGUyMi1iNTliLWQwOGY4MWMzNDQwOCIsInR5cCI6IkJlYXJlciIsImF6cCI6ImJhY2tlbmQtc2VydmljZSIsInNlc3Npb25fc3RhdGUiOiIyMGI0ZDE3NS0zNzhlLTQwNGUtOWMxNy1kMjNiYWU4OWQzNGYiLCJhY3IiOiIxIiwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm1hbmFnZXIiLCJvZmZsaW5lX2FjY2VzcyIsImRlZmF1bHQtcm9sZXMtcXVhcmt1cyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJlbWFpbCBwcm9maWxlIiwic2lkIjoiMjBiNGQxNzUtMzc4ZS00MDRlLTljMTctZDIzYmFlODlkMzRmIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJmaWxpcGUifQ.nyling9Lc6vDKMeE_3KBT6I78DKNGaqNQd1nP4C0E_B2-fZqcBECEFpqBBm9wKdWi6MsrsLCF5rAZw2swTupYlA9DN4e24nfeea9fjbm7HTPysV2T5mBfXRLteA3jWQ7E8YfkW9SN27HzMAO44216FEY66vN6g54t-pHMMfOkmwMfaHjxAQDwMp55DckPTTCST5b4i_bJlADAYqlEvJw6MP7C67w5MvoeDZIyRFYypo8as0YTAh5zyx2M-qo9MhyauJNgKSLEHBiUQolC1C9pRGQIVMRYLqCH1UHjO95PJnZLFjAqVwYoYuACyGDQbOrn55YOsBXwGPWDZyOTSkQaA";
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
                .when().delete("/18")
                .then()
                .statusCode(204);
    }
}