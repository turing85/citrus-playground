package de.turing85.citrus.playground.citrus;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import de.turing85.citrus.playground.citrus.configuration.Http;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.container.Async.Builder.async;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

@SuppressWarnings("unused")
public class GetHelloIT extends TestNGCitrusSpringSupport {
  @Test
  @CitrusTest
  public void getHello(@Optional @CitrusResource TestCaseRunner runner) {
    // GIVEN
    // @formatter:off
    runner.$(async().actions(
        http().server(Http.HTTP_SERVER_NAME)
            .receive().get("/greeting"),
        http().server(Http.HTTP_SERVER_NAME)
            .send()
                .response(HttpStatus.OK)
                .message().body("Hai")));

    // WHEN
    runner.$(http()
      .client(Http.SERVICE_CLIENT_NAME)
      .send()
        .get("/hello"));

    // THEN
    runner.$(http()
      .client(Http.SERVICE_CLIENT_NAME)
      .receive()
        .response(HttpStatus.OK)
        .message()
          .type(MessageType.PLAINTEXT)
          .body("Hai"));
    // @formatter:on
  }
}
