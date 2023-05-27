package de.turing85.citrus.tests.citrus.configuration;

import com.consol.citrus.dsl.endpoint.CitrusEndpoints;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.http.server.HttpServer;
import com.consol.citrus.http.server.HttpServerBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Http {

    public static final String SERVICE_CLIENT_NAME = "serviceClient";
    public static final String HTTP_SERVER_NAME = "httpServer";

    @Bean
    @Qualifier(SERVICE_CLIENT_NAME)
    public HttpClient serviceClient(@Value("${test-config.sut.url}") String sutUrl) {
        return CitrusEndpoints
          .http().client()
            .requestUrl(sutUrl)
        .build();
    }

    @Bean
    @Qualifier(HTTP_SERVER_NAME)
    public HttpServer httpServer(@Value("${test-config.http.server.port}") int port) {
        return new HttpServerBuilder()
            .port(port)
            .autoStart(true)
            .build();
    }
}
