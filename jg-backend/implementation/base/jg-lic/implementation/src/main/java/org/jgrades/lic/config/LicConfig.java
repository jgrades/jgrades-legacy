package org.jgrades.lic.config;

import com.google.common.collect.Lists;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.h2.tools.Server;
import org.jgrades.lic.api.config.LicApiConfig;
import org.jgrades.lic.utils.DbCloser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(
        basePackages = {"org.jgrades.lic.dao"},
        entityManagerFactoryRef = "licEntityManagerFactory",
        transactionManagerRef = "licTransactionManager")
@PropertySources({
        @PropertySource("classpath:jg-lic.properties"),
        @PropertySource(value = "file:${jgrades.application.properties.file}", ignoreResourceNotFound = true)
})
@EnableTransactionManagement
@EnableScheduling
@Import(LicApiConfig.class)
@ComponentScan("org.jgrades.lic")
public class LicConfig {
    @Value("${lic.db.path}")
    private String licDbPath;

    @Value("${lic.show.sql}")
    private String showSql;

    @Value("${lic.format.sql}")
    private String formatSql;

    @Value("${lic.schema.orm.policy}")
    private String schemaOrmPolicy;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfig() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    DataSource licDataSource() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        Server.createTcpServer("-tcpPort", "9092", "-tcpAllowOthers").start();


        HikariConfig dataSourceConfig = new HikariConfig();
        dataSourceConfig.setDriverClassName("org.h2.Driver");
        dataSourceConfig.setJdbcUrl("jdbc:h2:tcp://localhost/" + licDbPath);
        dataSourceConfig.setUsername("sa");
        dataSourceConfig.setPassword("");
        return new HikariDataSource(dataSourceConfig);
    }

    @Bean
    LocalContainerEntityManagerFactoryBean licEntityManagerFactory(DataSource licDataSource) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(licDataSource);
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan("org.jgrades.lic.entities");

        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        jpaProperties.put("hibernate.hbm2ddl.auto", schemaOrmPolicy);
        jpaProperties.put("hibernate.show_sql", showSql);
        jpaProperties.put("hibernate.format_sql", formatSql);
        entityManagerFactoryBean.setJpaProperties(jpaProperties);

        return entityManagerFactoryBean;
    }

    @Bean
    JpaTransactionManager licTransactionManager(EntityManagerFactory licEntityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(licEntityManagerFactory);
        return transactionManager;
    }

    @Bean
    Mapper mapper() {
        List<String> mappingFiles = Lists.newArrayList("lic_models_mapping.xml");
        return new DozerBeanMapper(mappingFiles);
    }

    @Bean
    DbCloser dbCloser(DataSource licDataSource) {
        return new DbCloser(licDataSource);
    }
}
