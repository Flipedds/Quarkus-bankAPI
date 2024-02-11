//package dev.filipe;
//
//import io.quarkus.test.common.http.TestHTTPEndpoint;
//import io.quarkus.test.junit.QuarkusTest;
//import org.junit.jupiter.api.Test;
//
//import dev.filipe.application.FrutasResource;
//
//import static io.restassured.RestAssured.given;
//
//@QuarkusTest
//@TestHTTPEndpoint(FrutasResource.class)
//class FrutasResourceTest {
//    @Test
//    void testFrutasEndpoint() {
//        given()
//          .when().get()
//          .then()
//             .statusCode(200);
//    }
//}