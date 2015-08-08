package org.jgrades.logging.logger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySources({
        @PropertySource("classpath:jg-logging.properties"),
        @PropertySource(value = "file:${jgrades.application.properties.file}", ignoreResourceNotFound = true)
})
public class LoggingConfig {
        @Bean
        public static PropertySourcesPlaceholderConfigurer propertyConfig() {
                return new PropertySourcesPlaceholderConfigurer();
        }
}
