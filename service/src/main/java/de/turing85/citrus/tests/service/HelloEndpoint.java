package de.turing85.citrus.tests.service;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

@Path("hello")
@Produces(MediaType.TEXT_PLAIN)
public class HelloEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(HelloEndpoint.class);

    private final MyRestClient myRestClient;

    public HelloEndpoint(@RestClient MyRestClient myRestClient) {
        this.myRestClient = myRestClient;
    }

    @GET
    public String hello() throws InterruptedException {
        Thread.sleep(Duration.ofSeconds(2).toMillis());
        String greeting =  "";
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("sending \"%s\" as response".formatted(greeting));
        }
        return greeting;
    }
}
