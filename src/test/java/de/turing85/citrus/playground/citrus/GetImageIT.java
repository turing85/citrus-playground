package de.turing85.citrus.playground.citrus;

import de.turing85.citrus.playground.citrus.configuration.HttpConfig;
import org.citrusframework.TestCaseRunner;
import org.citrusframework.annotations.CitrusResource;
import org.citrusframework.annotations.CitrusTest;
import org.citrusframework.context.TestContext;
import org.citrusframework.message.MessageHeaders;
import org.citrusframework.spi.Resources;
import org.citrusframework.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

import static org.citrusframework.container.Async.Builder.async;
import static org.citrusframework.dsl.MessageSupport.MessageHeaderSupport.fromHeaders;
import static org.citrusframework.http.actions.HttpActionBuilder.http;

@SuppressWarnings("unused")
public class GetImageIT extends TestNGCitrusSpringSupport {

  @Test
  @CitrusTest
  public void getImage(
      @Optional @CitrusResource TestCaseRunner runner,
      @Optional @CitrusResource TestContext context) {
    runner.variable("correlation-id", UUID.randomUUID().toString());
    runner.variable("image", new Resources.ClasspathResource("image.tif"));
    runner.variable("charset", StandardCharsets.ISO_8859_1);
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
                        context.getVariable("charset", Charset.class))));

    runner.when(
        http().client(HttpConfig.CLIENT).send()
            .get("/image")
            .message()
                .accept("image/tiff")
                .header("X-Correlation-ID", "${correlation-id}")
                .extract(fromHeaders().header(MessageHeaders.ID, "id-get-image")));

    runner.then(
        http().client(HttpConfig.CLIENT).receive()
            .response(HttpStatus.OK)
            .selector(Map.of(MessageHeaders.ID, "${id-get-image}"))
            .message()
                .contentType("image/tiff")
                .header("X-Correlation-ID", "${correlation-id}")
                .body(
                    context.getVariable("image", Resources.ClasspathResource.class),
                    context.getVariable("charset", Charset.class)));
    // @formatter:on
  }
}
