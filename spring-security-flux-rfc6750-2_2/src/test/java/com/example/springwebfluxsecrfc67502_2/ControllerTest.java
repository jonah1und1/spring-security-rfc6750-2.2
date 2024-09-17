package com.example.springwebfluxsecrfc67502_2;

import static com.github.tomakehurst.wiremock.client.WireMock.matching;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 8082)
@AutoConfigureWebClient
class ControllerTest {

    private static final String MINIMAL_INTROSPECTION_RESPONSE_TEMPLATE = """
            {
                "active": true
            }
            """;

    @Autowired
    private WebTestClient webClient;

    @BeforeEach
    void setUp() {
        stubFor(post("/introspection").withRequestBody(matching("token=.*"))
                                      .willReturn(ok().withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                                                      .withBody(MINIMAL_INTROSPECTION_RESPONSE_TEMPLATE)
                                      ));
    }

    @Test
    void postWithTokenAsHeader() {
        webClient.post()
                 .uri("/post")
                 .header(AUTHORIZATION, "Bearer anyValue")
                 .exchange()
                 .expectStatus().isOk();
    }

    @Test
    void getWithTokenAsQueryParam() {
        webClient.get()
                 .uri(uriBuilder -> uriBuilder.path("/get")
                                              .queryParam("access_token", "anyValue")
                                              .build())
                 .exchange()
                 .expectStatus().isOk();
    }

    @Test
    void postWithTokenInBody() {
        webClient.post()
                 .uri("/post")
                 .contentType(APPLICATION_FORM_URLENCODED)
                 .body(BodyInserters.fromFormData("access_token", "anyValue"))
                 .exchange()
                 .expectStatus().isOk();
    }

}