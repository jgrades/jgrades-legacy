package org.jgrades.monitor.config;

import org.jgrades.data.api.model.DataSourceDetails;
import org.jgrades.monitor.api.config.MonitorApiConfig;
import org.jgrades.monitor.dependency.DataSourceChecker;
import org.jgrades.monitor.dependency.DependencyChecker;
import org.jgrades.monitor.dependency.FileChecker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySources({
        @PropertySource("classpath:jg-monitor.properties"),
        @PropertySource(value = "classpath:jg-data.properties", ignoreResourceNotFound = true),
        @PropertySource(value = "file:${jgrades.application.properties.file}", ignoreResourceNotFound = true)
})
@Import(MonitorApiConfig.class)
@ComponentScan("org.jgrades.monitor")
public class MonitorConfig {
    @Value("${data.db.jdbc.url}")
    private String jdbcUrl;

    @Value("${data.db.username}")
    private String username;

    @Value("${data.db.password}")
    private String password;

    @Value("${data.jdbc.driver}")
    private String jdbcDriver;

    @Value("${lic.keystore.path}")
    private String licKeystorePath;

    @Value("${lic.sec.data.path}")
    private String licSecDataPath;

    @Value("${monitor.logging.xml.file.location}")
    private String loggingXmlFilePath;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfig() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean(name = "mainDataSourceChecker")
    public DependencyChecker mainDataSourceChecker() {
        DataSourceDetails dsDetails = new DataSourceDetails();
        dsDetails.setUrl(jdbcUrl);
        dsDetails.setUsername(username);
        dsDetails.setPassword(password);
        return new DataSourceChecker(dsDetails, jdbcDriver);
    }

    @Bean(name = "licKeystoreChecker")
    public DependencyChecker licKeystoreChecker() {
        return new FileChecker(licKeystorePath);
    }

    @Bean(name = "licSecDataChecker")
    public DependencyChecker licSecDataChecker() {
        return new FileChecker(licSecDataPath);
    }

    @Bean(name = "logbackXmlChecker")
    public DependencyChecker logbackXmlChecker() {
        return new FileChecker(loggingXmlFilePath);
    }


}
