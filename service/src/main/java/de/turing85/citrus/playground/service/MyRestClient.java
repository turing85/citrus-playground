package de.turing85.citrus.playground.service;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/greeting")
@Produces(MediaType.TEXT_PLAIN)
@RegisterRestClient(configKey = "external")
public interface MyRestClient {
  @GET
  Uni<String> getGreeting();
}
