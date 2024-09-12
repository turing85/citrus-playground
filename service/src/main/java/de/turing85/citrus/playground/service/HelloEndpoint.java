package de.turing85.citrus.playground.service;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("hello")
@Produces(MediaType.TEXT_PLAIN)
public class HelloEndpoint {
    private final MyRestClient myRestClient;

    public HelloEndpoint(@RestClient MyRestClient myRestClient) {
        this.myRestClient = myRestClient;
    }

    @GET
    public Uni<String> hello() {
        return myRestClient.getGreeting();
    }
}
