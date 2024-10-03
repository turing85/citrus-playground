package de.turing85.citrus.playground.citrus;

import de.turing85.citrus.playground.citrus.configuration.ConfigurationRoot;
import de.turing85.citrus.playground.citrus.configuration.Jms;
import org.citrusframework.TestCaseRunner;
import org.citrusframework.annotations.CitrusResource;
import org.citrusframework.annotations.CitrusTest;
import org.citrusframework.http.client.HttpClient;
import org.citrusframework.jms.endpoint.JmsEndpoint;
import org.citrusframework.message.MessageType;
import org.citrusframework.testng.spring.TestNGCitrusSpringSupport;
import de.turing85.citrus.playground.citrus.configuration.Http;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static org.citrusframework.actions.ReceiveMessageAction.Builder.receive;
import static org.citrusframework.http.actions.HttpActionBuilder.http;

@ContextConfiguration(classes = ConfigurationRoot.class)
public class GetHelloIT extends TestNGCitrusSpringSupport {
  @Autowired
  @Qualifier(Http.SERVICE_CLIENT_NAME)
  HttpClient serviceClient;

  @Autowired
  @Qualifier(Jms.ENDPOINT)
  JmsEndpoint jmsEndpoint;

  @Test
  @CitrusTest
  public void getHello(@Optional @CitrusResource TestCaseRunner runner) {
    // @formatter:off
    runner.variable("message", "foobar");
    runner.given(
        http()
            .client(serviceClient)
            .send()
            .post("/send")
            .message()
            .type(MessageType.PLAINTEXT)
            .body("${message}"));

    runner.when(
        http()
            .client(serviceClient)
            .receive()
            .response(HttpStatus.OK)
            .message()
            .type(MessageType.PLAINTEXT)
            .body("${message}"));

    runner.then(
        receive(jmsEndpoint)
            .message()
            .body("${message}"));
    // @formatter:on
  }
}
