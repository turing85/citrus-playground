package de.turing85.citrus.tests.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("hello")
@Produces(MediaType.TEXT_PLAIN)
public class HelloEndpoint {
    private final MyRestClient myRestClient;

    public HelloEndpoint(@RestClient MyRestClient myRestClient) {
        this.myRestClient = myRestClient;
    }

    @GET
    public String hello() {
        return myRestClient.getGreeting();
    }
}
