package de.turing85.citrus.tests.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/greeting")
@Produces(MediaType.TEXT_PLAIN)
@RegisterRestClient(configKey = "external")
public interface MyRestClient {
  @GET
  String getGreeting();
}
