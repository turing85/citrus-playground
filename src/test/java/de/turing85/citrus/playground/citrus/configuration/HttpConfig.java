package de.turing85.citrus.playground.citrus.configuration;

import org.citrusframework.http.client.HttpClient;
import org.citrusframework.http.client.HttpClientBuilder;
import org.citrusframework.http.server.HttpServer;
import org.citrusframework.http.server.HttpServerBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class HttpConfig {
  public static final String CLIENT = "client";
  public static final String SERVER = "server";

  @Bean(name = CLIENT)
  public HttpClient client(@Value("${test-config.http.server.port}") int port) {
    return new HttpClientBuilder()
        .requestUrl("http://localhost:%d".formatted(port))
        .build();
  }

  @Bean(name = SERVER)
  public HttpServer server(@Value("${test-config.http.server.port}") int port) {
    return new HttpServerBuilder()
        .port(port)
        .timeout(Duration.ofSeconds(1).toMillis())
        .autoStart(true)
        .build();
  }
}
