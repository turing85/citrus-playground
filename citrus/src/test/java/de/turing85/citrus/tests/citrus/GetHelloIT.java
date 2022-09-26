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

public class GetHelloIT extends TestNGCitrusSpringSupport {

  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  @Autowired
  @Qualifier("serviceClient")
  public HttpClient serviceClient;

  @Test
  @CitrusTest
  public void getHello() {
    $(echo("zzzZZZzzz"));
    $(sleep().seconds(15));
    $(echo("Let's go!"));

    // WHEN
    // @formatter:off
    $(http()
      .client(serviceClient)
      .send()
        .get("/hello"));

    // THEN
    $(http()
      .client(serviceClient)
      .receive()
        .response(HttpStatus.OK)
        .message()
          .type(MessageType.PLAINTEXT)
          .body("Hello"));
    // @formatter:on
  }
}
