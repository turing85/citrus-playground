package de.turing85.citrus.playground.citrus.configuration;

import org.citrusframework.config.CitrusSpringConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ CitrusSpringConfig.class, Http.class, Validators.class })
public class ConfigurationRoot {
}
