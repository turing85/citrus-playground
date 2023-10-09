package de.turing85.citrus.playground.citrus;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import de.turing85.citrus.playground.citrus.configuration.Endpoints;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import org.xmlunit.diff.DefaultNodeMatcher;
import org.xmlunit.diff.ElementSelectors;

import java.util.UUID;

import static com.consol.citrus.actions.ReceiveMessageAction.Builder.receive;
import static com.consol.citrus.actions.SendMessageAction.Builder.send;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.xmlunit.matchers.CompareMatcher.isSimilarTo;

@Test(testName = "XML Integration test", groups = { "local" })
public class XmlIT extends TestNGCitrusSpringSupport {
  @Test(testName = "Foo Test")
  @CitrusTest
  public void foo(
      @Optional @CitrusResource TestCaseRunner runner,
      @Optional @CitrusResource TestContext context) {
    final UUID id = UUID.randomUUID();
    runner.when(send(Endpoints.FOO_ENDPOINT_NAME)
        .message()
          .type(MessageType.XML)
          .body("<foo><boolean>true</boolean><int>3</int></foo>"));
    runner.then(receive(Endpoints.FOO_ENDPOINT_NAME)
            .message()
              .type(MessageType.XML)
              .name(id.toString()));
    assertThat(
        context.getMessageStore().getMessage(id.toString()).getPayload(String.class),
        isSimilarTo("""
                <foo>
                    <boolean>true</boolean>
                    <int>3</int>
                </foo>""")
            .ignoreElementContentWhitespace()
            .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byName)));
  }
}
