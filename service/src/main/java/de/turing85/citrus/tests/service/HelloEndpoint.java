package de.turing85.citrus.tests.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("hello")
@Produces(MediaType.TEXT_PLAIN)
public class HelloEndpoint {
    @GET
    public String hello() {
        return "Hello";
    }
}
