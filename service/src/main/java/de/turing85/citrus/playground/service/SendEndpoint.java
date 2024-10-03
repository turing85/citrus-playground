package de.turing85.citrus.playground.service;

import io.quarkus.logging.Log;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.JMSProducer;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.apache.activemq.artemis.jms.client.ActiveMQDestination;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Path("send")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.TEXT_PLAIN)
public class SendEndpoint {
  private final ConnectionFactory connectionFactory;
  private final String sendTopic;

  public SendEndpoint(
      @SuppressWarnings("CdiInjectionPointsInspection") ConnectionFactory connectionFactory,
      @ConfigProperty(name = "send.topic") String sendTopic) {
    this.connectionFactory = connectionFactory;
    this.sendTopic = sendTopic;
  }

  @POST
  @Produces(MediaType.TEXT_PLAIN)
  public String send(String message) {
    try (JMSContext context = connectionFactory.createContext()) {
      Log.infof("sending message \"%s\"", message);
      JMSProducer producer = context.createProducer();
      producer.send(ActiveMQDestination.createTopic(sendTopic), message);
      Log.info("message sent");
    } catch (Exception e) {
      Log.error("Error occurred during send", e);
      throw e;
    }
    return message;
  }
}
