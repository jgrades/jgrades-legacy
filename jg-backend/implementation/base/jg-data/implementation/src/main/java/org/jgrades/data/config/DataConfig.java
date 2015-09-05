package org.jgrades.data.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(
        basePackages = {"org.jgrades.data.api.dao"},
        entityManagerFactoryRef = "mainEntityManagerFactory",
        transactionManagerRef = "mainTransactionManager")
@PropertySources({
        @PropertySource("classpath:jg-data.properties"),
        @PropertySource(value = "file:${jgrades.application.properties.file}", ignoreResourceNotFound = true)
})
@EnableTransactionManagement
@ComponentScan("org.jgrades.data")
public class DataConfig {
    @Value("${jgrades.application.properties.file}")
    private String appPropertiesFilePath;

    @Value("${data.db.jdbc.url}")
    private String jdbcUrl;

    @Value("${data.db.username}")
    private String username;

    @Value("${data.db.password}")
    private String password;

    @Value("${data.show.sql:false}")
    private String showSql;

    @Value("${data.format.sql:false}")
    private String formatSql;

    @Value("${data.schema.orm.policy}")
    private String schemaOrmPolicy;

    @Value("#{'${jgrades.entities.packages}'.split(',')}")
    private List<String> packagesToScan;


    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfig() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean(destroyMethod = "close")
    DataSource mainDataSource() {
        HikariConfig dataSourceConfig = new HikariConfig();
        dataSourceConfig.setDriverClassName("org.postgresql.Driver");
        dataSourceConfig.setJdbcUrl(jdbcUrl);
        dataSourceConfig.setUsername(username);
        dataSourceConfig.setPassword(password);
        return new HikariDataSource(dataSourceConfig);
    }

    @Bean
    LocalContainerEntityManagerFactoryBean mainEntityManagerFactory(DataSource mainDataSource) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(mainDataSource);
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan(packagesToScan.toArray(new String[]{}));

        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        jpaProperties.put("hibernate.hbm2ddl.auto", schemaOrmPolicy);
        jpaProperties.put("hibernate.show_sql", showSql);
        jpaProperties.put("hibernate.format_sql", formatSql);
        entityManagerFactoryBean.setJpaProperties(jpaProperties);

        return entityManagerFactoryBean;
    }

    @Bean
    JpaTransactionManager mainTransactionManager(EntityManagerFactory mainEntityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(mainEntityManagerFactory);
        return transactionManager;
    }

    @Bean
    public org.apache.commons.configuration.Configuration appConfiguration() throws IOException, ConfigurationException {
        File appPropertiesFile = new File(appPropertiesFilePath);
        if (!appPropertiesFile.exists()) {
            appPropertiesFile.createNewFile();
        }
        return new PropertiesConfiguration(appPropertiesFile);
    }
}
