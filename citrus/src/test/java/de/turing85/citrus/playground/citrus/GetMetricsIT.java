package de.turing85.citrus.playground.citrus;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import de.turing85.citrus.playground.citrus.configuration.Http;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

@SuppressWarnings("unused")
public class GetMetricsIT extends TestNGCitrusSpringSupport {
  @Test
  @CitrusTest
  public void getMetrics(@Optional @CitrusResource TestCaseRunner runner) {
    // WHEN
    // @formatter:off
    runner.$(http()
      .client(Http.SERVICE_CLIENT_NAME)
      .send()
        .get("/q/metrics"));

    // THEN
    runner.$(http()
      .client(Http.SERVICE_CLIENT_NAME)
      .receive()
        .response(HttpStatus.OK));
    // @formatter:on
  }
}
