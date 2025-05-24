package de.turing85.citrus.playground.citrus;

import de.turing85.citrus.playground.citrus.configuration.ConfigurationRoot;
import org.citrusframework.TestCaseRunner;
import org.citrusframework.annotations.CitrusResource;
import org.citrusframework.annotations.CitrusTest;
import org.citrusframework.http.client.HttpClient;
import org.citrusframework.http.server.HttpServer;
import org.citrusframework.message.MessageType;
import org.citrusframework.testng.spring.TestNGCitrusSpringSupport;
import de.turing85.citrus.playground.citrus.configuration.Http;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static org.citrusframework.actions.EchoAction.Builder.echo;
import static org.citrusframework.actions.SleepAction.Builder.sleep;
import static org.citrusframework.container.Async.Builder.async;
import static org.citrusframework.http.actions.HttpActionBuilder.http;

@ContextConfiguration(classes = ConfigurationRoot.class)
public class GetHelloIT extends TestNGCitrusSpringSupport {
  @Autowired
  @Qualifier(Http.SERVICE_CLIENT_NAME)
  HttpClient serviceClient;

  @Autowired
  @Qualifier(Http.HTTP_SERVER_NAME)
  HttpServer httpServer;

  @Test
  @CitrusTest
  public void getHello(@Optional @CitrusResource TestCaseRunner runner) {
    // @formatter:off
    runner.$(echo("zzzZZZzzz"));
    runner.given(sleep().seconds(2));
    runner.$(echo("WAKE UP"));
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
