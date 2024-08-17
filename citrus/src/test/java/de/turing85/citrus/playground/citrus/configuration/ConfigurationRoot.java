package de.turing85.citrus.playground.citrus.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.io.IoBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({HttpConfig.class})
public class ConfigurationRoot {
  static {
    System.setOut(IoBuilder
        .forLogger(LogManager.getLogger("SYSTEM_OUT"))
        .buildPrintStream());
  }
}
