package de.turing85.citrus.playground.citrus.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ Endpoints.class, Http.class, Validators.class })
public class ConfigurationRoot {
}
