package de.turing85.citrus.tests.citrus;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;

import static com.consol.citrus.actions.EchoAction.Builder.echo;
import static com.consol.citrus.actions.SleepAction.Builder.sleep;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class GetMetricsIT extends TestNGCitrusSpringSupport {

  @Autowired
  @Qualifier("serviceClient")
  private HttpClient serviceClient;

  @Test
  @CitrusTest
  public void getMetrics() {
    $(echo("zzzZZZzzz"));
    $(sleep().seconds(15f));
    $(echo("Let's go!"));

    // WHEN
    // @formatter:off
    $(http()
      .client(serviceClient)
      .send()
        .get("/q/metrics"));

    // THEN
    $(http()
      .client(serviceClient)
      .receive()
        .response(HttpStatus.OK));
    // @formatter:on
  }
}
