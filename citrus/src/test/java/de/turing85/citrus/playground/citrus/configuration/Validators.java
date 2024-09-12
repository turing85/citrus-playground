package de.turing85.citrus.playground.citrus.configuration;

import org.citrusframework.validation.text.PlainTextMessageValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Validators {

  @Bean
  PlainTextMessageValidator defaultPlainTextMessageValidator() {
    return new PlainTextMessageValidator();
  }
}
