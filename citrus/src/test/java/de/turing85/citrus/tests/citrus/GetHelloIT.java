package de.turing85.citrus.tests.citrus;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import de.turing85.citrus.tests.citrus.configuration.Http;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;

import static com.consol.citrus.actions.EchoAction.Builder.echo;
import static com.consol.citrus.actions.SleepAction.Builder.sleep;
import static com.consol.citrus.container.Async.Builder.async;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

@SuppressWarnings("unused")
public class GetHelloIT extends TestNGCitrusSpringSupport {
  @Test
  @CitrusTest
  public void getHello() {
    $(echo("zzzZZZzzz"));
    $(sleep().seconds(15));
    $(echo("Let's go!"));

    // GIVEN
    // @formatter:off
    $(async().actions(
        http().server(Http.HTTP_SERVER_NAME)
            .receive().get("/greeting"),
        http().server(Http.HTTP_SERVER_NAME)
            .send()
                .response(HttpStatus.OK)
                .message().body("Hai")));

    // WHEN
    $(http()
      .client(Http.SERVICE_CLIENT_NAME)
      .send()
        .get("/hello"));

    // THEN
    $(http()
      .client(Http.SERVICE_CLIENT_NAME)
      .receive()
        .response(HttpStatus.OK)
        .message()
          .type(MessageType.PLAINTEXT)
          .body("Hai"));
    // @formatter:on
  }
}
