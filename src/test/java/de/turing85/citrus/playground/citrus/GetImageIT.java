package de.turing85.citrus.playground.citrus;

import de.turing85.citrus.playground.citrus.configuration.HttpConfig;
import org.citrusframework.TestCaseRunner;
import org.citrusframework.annotations.CitrusResource;
import org.citrusframework.annotations.CitrusTest;
import org.citrusframework.context.TestContext;
import org.citrusframework.spi.Resources;
import org.citrusframework.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static org.citrusframework.container.Async.Builder.async;
import static org.citrusframework.http.actions.HttpActionBuilder.http;

@SuppressWarnings("unused")
public class GetImageIT extends TestNGCitrusSpringSupport {

  @Test
  @CitrusTest
  public void getImage(
      @Optional @CitrusResource TestCaseRunner runner,
      @Optional @CitrusResource TestContext context) {
    runner.variable("image", new Resources.ClasspathResource("image.tif"));
    runner.variable("correlation-id", UUID.randomUUID().toString());
    // @formatter:off
    runner.given(
        async().actions(
            http().server(HttpConfig.SERVER).receive()
                .get("/image")
                .message()
                    .accept("image/tiff")
                    .header("X-Correlation-Id", "${correlation-id}"),
            http().server(HttpConfig.SERVER).send()
                .response(HttpStatus.OK)
                .message()
                    .contentType("image/tiff")
                    .header("X-Correlation-ID", "${correlation-id}")
                    .body(
                        context.getVariable("image", Resources.ClasspathResource.class),
                        StandardCharsets.ISO_8859_1)));

    runner.when(
        http().client(HttpConfig.CLIENT).send()
            .get("/image")
            .message()
                .accept("image/tiff")
                .header("X-Correlation-ID", "${correlation-id}"));

    runner.then(
        http().client(HttpConfig.CLIENT).receive()
            .response(HttpStatus.OK)
            .message()
                .contentType("image/tiff")
                .header("X-Correlation-ID", "${correlation-id}")
                .body(
                    context.getVariable("image", Resources.ClasspathResource.class),
                    StandardCharsets.ISO_8859_1));
    // @formatter:on
  }
}
