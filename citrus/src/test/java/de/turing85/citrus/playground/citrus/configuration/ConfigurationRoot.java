package de.turing85.citrus.playground.citrus.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ BeforeTestSequence.class, Http.class, Jms.class })
public class ConfigurationRoot {
}
