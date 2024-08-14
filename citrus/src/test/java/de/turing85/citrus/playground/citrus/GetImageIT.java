package de.turing85.citrus.playground.citrus;

import com.google.common.truth.Truth;
import org.citrusframework.TestCaseRunner;
import org.citrusframework.annotations.CitrusResource;
import org.citrusframework.annotations.CitrusTest;
import org.citrusframework.context.TestContext;
import org.citrusframework.spi.Resources;
import org.citrusframework.testng.spring.TestNGCitrusSpringSupport;
import de.turing85.citrus.playground.citrus.configuration.Http;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.citrusframework.container.Async.Builder.async;
import static org.citrusframework.container.Sequence.Builder.sequential;
import static org.citrusframework.http.actions.HttpActionBuilder.http;

@SuppressWarnings("unused")
public class GetImageIT extends TestNGCitrusSpringSupport {
  @Test
  @CitrusTest
  public void getImage(
      @Optional @CitrusResource TestCaseRunner runner,
      @Optional@CitrusResource TestContext context) throws IOException {
    // GIVEN
    final byte[] expected = new Resources.ClasspathResource("image.tif")
        .getInputStream()
        .readAllBytes();
    // @formatter:off
    runner.$(async().actions(
        sequential().actions(
            http()
                .server(Http.HTTP_SERVER_NAME)
                .receive().get("/image"),
          http()
              .server(Http.HTTP_SERVER_NAME)
              .send()
                  .response(HttpStatus.OK)
                  .message()
                      .contentType("image/tiff")
                      .body(
                          new Resources.ClasspathResource("image.tif"),
                          StandardCharsets.ISO_8859_1))));

    // WHEN
    runner.$(http()
        .client(Http.SERVICE_CLIENT_NAME)
        .send().get("/image"));
    runner.$(http()
        .client(Http.SERVICE_CLIENT_NAME)
        .receive()
            .response(HttpStatus.OK)
            .message()
                .name("client-receive")
                .contentType("image/tiff"));
    final byte[] actual = context
        .getMessageStore()
        .getMessage("client-receive")
        .getPayload(String.class)
        .getBytes(StandardCharsets.ISO_8859_1);

    // THEN
    runner.$(unused -> {
      Truth.assertThat(actual.length).isEqualTo(expected.length);
      Truth.assertThat(actual).isEqualTo(expected);
    });
    // @formatter:on
  }
}
