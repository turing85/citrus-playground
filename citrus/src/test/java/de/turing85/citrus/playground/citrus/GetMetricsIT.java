package de.turing85.citrus.playground.citrus;

import de.turing85.citrus.playground.citrus.configuration.ConfigurationRoot;
import de.turing85.citrus.playground.citrus.configuration.Http;
import org.citrusframework.TestCaseRunner;
import org.citrusframework.annotations.CitrusEndpoint;
import org.citrusframework.annotations.CitrusResource;
import org.citrusframework.annotations.CitrusTest;
import org.citrusframework.http.client.HttpClient;
import org.citrusframework.junit.jupiter.spring.CitrusSpringSupport;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;

import static org.citrusframework.http.actions.HttpActionBuilder.http;

@CitrusSpringSupport
@ContextConfiguration(classes = ConfigurationRoot.class)
class GetMetricsIT {
  @CitrusEndpoint(name = Http.SERVICE_CLIENT_NAME)
  HttpClient serviceClient;

  @Test
  @CitrusTest
  void getMetrics(@CitrusResource TestCaseRunner runner) {
    // @formatter:off
    runner.when(
        http()
            .client(serviceClient)
            .send()
            .get("/q/metrics"));

    runner.then(
        http()
            .client(serviceClient)
            .receive()
            .response(HttpStatus.OK));
    // @formatter:on
  }
}
