package de.turing85.citrus.playground.citrus;

import de.turing85.citrus.playground.citrus.configuration.ConfigurationRoot;
import de.turing85.citrus.playground.citrus.configuration.Http;
import org.citrusframework.TestCaseRunner;
import org.citrusframework.annotations.CitrusEndpoint;
import org.citrusframework.annotations.CitrusResource;
import org.citrusframework.annotations.CitrusTest;
import org.citrusframework.http.client.HttpClient;
import org.citrusframework.http.server.HttpServer;
import org.citrusframework.junit.jupiter.spring.CitrusSpringSupport;
import org.citrusframework.message.MessageType;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;

import static org.citrusframework.container.Async.Builder.async;
import static org.citrusframework.http.actions.HttpActionBuilder.http;

@CitrusSpringSupport
@ContextConfiguration(classes = ConfigurationRoot.class)
class GetHelloIT {
  @CitrusEndpoint(name = Http.SERVICE_CLIENT_NAME)
  HttpClient serviceClient;

  @CitrusEndpoint(name = Http.HTTP_SERVER_NAME)
  HttpServer httpServer;

  @Test
  @CitrusTest
  void getHello(@CitrusResource TestCaseRunner runner) {
    // @formatter:off
    runner.variable("payload", "Hai");
    runner.given(
        async().actions(
            http()
                .server(httpServer)
                .receive()
                .get("/greeting"),
            http()
                .server(httpServer)
                .send()
                .response(HttpStatus.OK)
                .message()
                .body("${payload}")));

    runner.when(
        http()
            .client(serviceClient)
            .send()
            .get("/hello"));

    runner.then(
        http()
            .client(serviceClient)
            .receive()
            .response(HttpStatus.OK)
            .message()
            .type(MessageType.PLAINTEXT)
            .body("${payload}"));
    // @formatter:on
  }
}
