package de.turing85.citrus.playground.citrus.configuration;

import static org.citrusframework.jms.actions.PurgeJmsQueuesAction.Builder.purgeQueues;

import jakarta.jms.ConnectionFactory;
import org.citrusframework.container.SequenceBeforeTest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeforeTestSequence {
  @Bean
  public SequenceBeforeTest beforeTest(
      @Qualifier(Jms.CONNECTION_FACTORY) ConnectionFactory connectionFactory,
      @Value("${test-config.artemis.receive.fqqn}") String fqqn) {
    return SequenceBeforeTest.Builder.beforeTest()
        .actions(
            purgeQueues()
                .connectionFactory(connectionFactory)
                .queue(fqqn))
        .build();
  }
}
