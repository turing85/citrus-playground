package de.turing85.citrus.playground.citrus.configuration;

import org.citrusframework.dsl.endpoint.CitrusEndpoints;
import org.citrusframework.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Http {
    public static final String SERVICE_CLIENT_NAME = "serviceClient";

    @Bean(SERVICE_CLIENT_NAME)
    public HttpClient serviceClient(@Value("${test-config.sut.url}") String sutUrl) {
        return CitrusEndpoints
          .http().client()
            .requestUrl(sutUrl)
        .build();
    }
}
