package com.github.muirandy.docs.yatspec.distributed.example.stub;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@QuarkusTest
public class StubExampleTest {
    private static final String BASE_PATH = "/reverse/";

    @Test
    void shouldReturnEmpty200ForEmptyRequest() {
        Response response = given()
                .header("Content-Type", "text/plain" +
                        "" +
                        "")
                .when()
                .post(BASE_PATH);
        String responseBody = response.getBody().asString();

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(responseBody).isEmpty();
    }

    @Test
    void shouldReturnReversedString() {
        String input = "helloWorld";
        Response response = given()
                .header("Content-Type", "text/plain")
                .when()
                .body(input)
                .post(BASE_PATH);
        String responseBody = response.getBody().asString();

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(responseBody).isEqualTo("dlroWolleh");
    }
}