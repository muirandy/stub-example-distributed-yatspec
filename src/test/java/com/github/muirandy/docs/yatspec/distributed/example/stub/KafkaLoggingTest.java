package com.github.muirandy.docs.yatspec.distributed.example.stub;

import com.github.muirandy.docs.yatspec.distributed.log.kafka.KafkaConfiguration;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.json.bind.JsonbBuilder;

import static com.github.muirandy.docs.yatspec.distributed.example.stub.KafkaResource.KAFKA_HOSTNAME;
import static com.github.muirandy.docs.yatspec.distributed.example.stub.KafkaResource.KAFKA_PORT;
import static io.restassured.RestAssured.given;

@QuarkusTest
@QuarkusTestResource(KafkaResource.class)
public class KafkaLoggingTest {

    private static final String BASE_PATH = "/reverse";
    private static final String SOURCE_SERVICE_NAME = "Source Service";
    private static final String STUB_SERVICE_NAME = "Stub Service";

    @Test
    void canConfigureKafkaLogger() {
        KafkaConfiguration kafkaConfiguration = createKafkaConfiguration();

        String input = JsonbBuilder.create().toJson(kafkaConfiguration);
        given()
                .header("Content-Type", "application/json")
                .when()
                .body(input)
                .post(BASE_PATH + "/configure")
                .then()
                .statusCode(204);
    }

    @Test
    void canDeleteKafkaLogger() {
        given()
                .when()
                .delete(BASE_PATH + "/configure")
                .then()
                .statusCode(204);
    }

    private KafkaConfiguration createKafkaConfiguration() {
        String kafkaHost = System.getProperty(KAFKA_HOSTNAME);
        String kafkaPort = System.getProperty(KAFKA_PORT);
        return new KafkaConfiguration(SOURCE_SERVICE_NAME, STUB_SERVICE_NAME, kafkaHost, Integer.valueOf(kafkaPort));
    }
}
