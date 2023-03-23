package de.turing85.citrus.tests.citrus.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ Http.class, Validators.class })
public class ConfigurationRoot {
}
