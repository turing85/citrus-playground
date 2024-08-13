package de.turing85.citrus.playground.citrus.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ Http.class })
public class ConfigurationRoot {
}
