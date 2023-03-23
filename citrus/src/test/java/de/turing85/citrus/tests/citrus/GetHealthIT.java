package de.turing85.citrus.tests.citrus;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import de.turing85.citrus.tests.citrus.configuration.Http;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;

import static com.consol.citrus.actions.EchoAction.Builder.echo;
import static com.consol.citrus.actions.SleepAction.Builder.sleep;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;
import static com.consol.citrus.validation.json.JsonPathMessageValidationContext.Builder.jsonPath;

@SuppressWarnings("unused")
public class GetHealthIT extends TestNGCitrusSpringSupport {
  @Test
  @CitrusTest
  public void getHealth() {
    $(echo("zzzZZZzzz"));
    $(sleep().seconds(15));
    $(echo("Let's go!"));

    // WHEN
    // @formatter:off
    $(http()
      .client(Http.SERVICE_CLIENT_NAME)
      .send()
        .get("/q/health"));

    // THEN
    $(http()
      .client(Http.SERVICE_CLIENT_NAME)
      .receive()
        .response(HttpStatus.OK)
        .message()
          .type(MessageType.JSON)
          .validate(jsonPath()
            .expression("$.status", "UP")));
    // @formatter:on
  }
}
