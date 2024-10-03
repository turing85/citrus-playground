package de.turing85.citrus.playground.citrus.configuration;

import jakarta.jms.ConnectionFactory;
import org.apache.activemq.artemis.jms.client.ActiveMQJMSConnectionFactory;
import org.citrusframework.jms.endpoint.JmsEndpoint;
import org.citrusframework.jms.endpoint.JmsEndpointBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Jms {
  public static final String ENDPOINT = "JMS_ENDPOINT";
  public static final String CONNECTION_FACTORY = "CONNECTION_FACTORY";

  @Bean
  @Qualifier(CONNECTION_FACTORY)
  public ConnectionFactory connectionFactory(
      @Value("${test-config.artemis.url}") String url,
      @Value("${test-config.artemis.username}") String username,
      @Value("${test-config.artemis.password}") String password) {
    return new ActiveMQJMSConnectionFactory(url, username, password);
  }

  @Bean
  @Qualifier(ENDPOINT)
  public JmsEndpoint jmsEndpoint(
      @Qualifier(CONNECTION_FACTORY) ConnectionFactory connectionFactory,
      @Value("${test-config.artemis.receive.fqqn}") String fqqn) {
    return new JmsEndpointBuilder()
        .connectionFactory(connectionFactory)
        .pubSubDomain(true)
        .destination(fqqn)
        .autoStart(true)
        .build();
  }
}
