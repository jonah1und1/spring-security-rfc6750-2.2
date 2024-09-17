package com.example.springsecuritymvcrfc67502_2;

import static com.github.tomakehurst.wiremock.client.WireMock.matching;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static io.restassured.RestAssured.given;
import static io.restassured.config.EncoderConfig.encoderConfig;
import static io.restassured.http.ContentType.JSON;
import static org.springframework.http.HttpStatus.OK;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;

import com.github.tomakehurst.wiremock.client.WireMock;

import io.restassured.RestAssured;
import io.restassured.http.Header;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@AutoConfigureWireMock(port = 8082)
class ControllerTest {

    private static final String MINIMAL_INTROSPECTION_RESPONSE_TEMPLATE = """
            {
                "active": true
            }
            """;

    @BeforeEach
    void setUp() {
        stubFor(WireMock.post("/introspection").withRequestBody(matching("token=.*"))
                        .willReturn(ok().withHeader("Content-Type", JSON.toString())
                                        .withBody(MINIMAL_INTROSPECTION_RESPONSE_TEMPLATE)
                        ));
    }

    @Test
    void postWithTokenAsHeader() {
        given().header(new Header("Authorization", "Bearer anyValue"))
               .when()
               .port(8080)
               .post("/post")
               .then()
               .statusCode(OK.value());
    }

    @Test
    void getWithTokenAsQueryParam() {
        given().param("access_token", "anyValue")
               .when()
               .port(8080)
               .get("/get")
               .then()
               .statusCode(OK.value());
    }

    @Test
    void postWithTokenInBody() {
        given().config(RestAssured.config()
                                  .encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)))
               .header(new Header("Content-Type", "application/x-www-form-urlencoded"))
               .formParam("access_token", "anyValue")
               .when()
               .post("/post")
               .then()
               .statusCode(OK.value());
    }

}