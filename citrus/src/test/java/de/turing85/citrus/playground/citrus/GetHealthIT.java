package de.turing85.citrus.playground.citrus;

import de.turing85.citrus.playground.citrus.configuration.ConfigurationRoot;
import org.citrusframework.TestCaseRunner;
import org.citrusframework.annotations.CitrusResource;
import org.citrusframework.annotations.CitrusTest;
import org.citrusframework.http.client.HttpClient;
import org.citrusframework.message.MessageType;
import org.citrusframework.testng.spring.TestNGCitrusSpringSupport;
import de.turing85.citrus.playground.citrus.configuration.Http;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static org.citrusframework.http.actions.HttpActionBuilder.http;
import static org.citrusframework.validation.json.JsonPathMessageValidationContext.Builder.jsonPath;

@ContextConfiguration(classes = ConfigurationRoot.class)
public class GetHealthIT extends TestNGCitrusSpringSupport {
  @Autowired
  @Qualifier(Http.SERVICE_CLIENT_NAME)
  HttpClient serviceClient;

  @Test
  @CitrusTest
  public void getHealth(@Optional @CitrusResource TestCaseRunner runner) {
    // @formatter:off
    runner.when(
        http()
            .client(serviceClient)
            .send()
            .get("/q/health"));

    runner.then(
        http()
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
