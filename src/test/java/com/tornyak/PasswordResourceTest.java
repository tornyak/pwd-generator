package com.tornyak;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class PasswordResourceTest {

    @Test
    void generatePassword() {
        given()
                .when().get("/password")
                .then()
                .statusCode(200)
                .body(is("admin1234"));
    }
}