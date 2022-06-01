package de.turing85.citrus.tests.citrus.configuration;

import com.consol.citrus.dsl.endpoint.CitrusEndpoints;
import com.consol.citrus.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpClients {

    @Value("${test-config.sut.url}")
    String sutUrl;

    @Bean
    @Qualifier("serviceClient")
    public HttpClient serviceClient() {
        return CitrusEndpoints
          .http().client()
            .requestUrl(sutUrl)
        .build();
    }
}
