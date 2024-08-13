package de.turing85.citrus.playground.citrus;

import com.google.common.truth.Truth;
import org.citrusframework.TestCaseRunner;
import org.citrusframework.annotations.CitrusResource;
import org.citrusframework.annotations.CitrusTest;
import org.citrusframework.context.TestContext;
import org.citrusframework.message.MessageType;
import org.citrusframework.spi.Resources;
import org.citrusframework.testng.spring.TestNGCitrusSpringSupport;
import de.turing85.citrus.playground.citrus.configuration.Http;
import org.citrusframework.validation.interceptor.BinaryMessageProcessor;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import static org.citrusframework.container.Async.Builder.async;
import static org.citrusframework.http.actions.HttpActionBuilder.http;

@SuppressWarnings("unused")
public class GetImageIT extends TestNGCitrusSpringSupport {
  @Test
  @CitrusTest
  public void getImage(
      @Optional @CitrusResource TestCaseRunner runner,
      @Optional@CitrusResource TestContext context) throws IOException {
    try (InputStream resourceAsStream = Objects.requireNonNull(getClass().getClassLoader()
            .getResourceAsStream("image.tif"))) {
      // GIVEN
      // @formatter:off
      byte[] expected = resourceAsStream.readAllBytes();
      runner.$(async().actions(
          http().server(Http.HTTP_SERVER_NAME)
              .receive().get("/image"),
        http().server(Http.HTTP_SERVER_NAME)
            .send()
                .response(HttpStatus.OK)
                .message()
                    .type(MessageType.BINARY)
                    .body(new Resources.ClasspathResource("image.tif"))
                    .process(BinaryMessageProcessor.Builder.toBinary())));

      // WHEN
      runner.$(http()
          .client(Http.SERVICE_CLIENT_NAME)
          .send().get("/image"));
      runner.$(http()
          .client(Http.SERVICE_CLIENT_NAME)
          .receive()
              .response(HttpStatus.OK)
              .message().name("client-receive"));
      byte[] actual = context
          .getMessageStore()
          .getMessage("client-receive")
          .getPayload(byte[].class);

      // THEN
      runner.$(unused -> {
        Truth.assertThat(actual.length).isEqualTo(expected.length);
        Truth.assertThat(actual).isEqualTo(expected);
      });
    }
  }
}
