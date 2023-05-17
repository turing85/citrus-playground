package de.turing85.citrus.tests.citrus;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import de.turing85.citrus.tests.citrus.configuration.Http;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

@SuppressWarnings("unused")
public class GetMetricsIT extends TestNGCitrusSpringSupport {
  @Test
  @CitrusTest
  public void getMetrics() {
    // WHEN
    // @formatter:off
    $(http()
      .client(Http.SERVICE_CLIENT_NAME)
      .send()
        .get("/q/metrics"));

    // THEN
    $(http()
      .client(Http.SERVICE_CLIENT_NAME)
      .receive()
        .response(HttpStatus.OK));
    // @formatter:on
  }
}
