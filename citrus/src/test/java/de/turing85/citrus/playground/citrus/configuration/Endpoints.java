package de.turing85.citrus.playground.citrus.configuration;

import com.consol.citrus.channel.ChannelEndpoint;
import com.consol.citrus.channel.ChannelEndpointBuilder;
import com.consol.citrus.channel.MessageSelectingQueueChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.MessageChannel;

public class Endpoints {

  public static final String FOO_QUEUE_NAME = "foo-queue";
  public static final String FOO_ENDPOINT_NAME = "fooEndpoint";

  @Bean(FOO_QUEUE_NAME)
  public MessageSelectingQueueChannel fooQueue() {
    return new MessageSelectingQueueChannel();
  }

  @Bean(FOO_ENDPOINT_NAME)
  public ChannelEndpoint fooEndpoint(
      @Autowired
      @Qualifier(FOO_QUEUE_NAME)
      MessageChannel fooQueue) {
    return new ChannelEndpointBuilder()
        .channel(fooQueue)
        .build();
  }
}
