package de.turing85.citrus.tests.citrus;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;

import static com.consol.citrus.actions.EchoAction.Builder.echo;
import static com.consol.citrus.actions.SleepAction.Builder.sleep;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;
import static com.consol.citrus.validation.json.JsonPathMessageValidationContext.Builder.jsonPath;

public class GetHealthIT extends TestNGCitrusSpringSupport {

  @Autowired
  @Qualifier("serviceClient")
  private HttpClient serviceClient;

  @Test
  @CitrusTest
  public void getHealth() {
    $(echo("zzzZZZzzz"));
    $(sleep().seconds(15f));
    $(echo("Let's go!"));

    // WHEN
    // @formatter:off
    $(http()
      .client(serviceClient)
      .send()
        .get("/q/health"));

    // THEN
    $(http()
      .client(serviceClient)
      .receive()
        .response(HttpStatus.OK)
        .message()
          .type(MessageType.JSON)
          .validate(jsonPath()
            .expression("$.status", "UP")));
    // @formatter:on
  }
}
