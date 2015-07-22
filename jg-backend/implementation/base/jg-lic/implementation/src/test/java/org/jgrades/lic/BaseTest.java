package org.jgrades.lic;

import com.google.common.collect.Lists;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class)
public abstract class BaseTest {

    @Configuration
    @EnableJpaRepositories(basePackages = {"org.jgrades.lic.dao"})
    @EnableTransactionManagement
    static class ContextConfiguration {
        @Rule
        public TemporaryFolder temporaryFolder = new TemporaryFolder();

        private String licDbPath;
        private String showSql = "true";
        private String formatSql = "true";
        private String schemaOrmPolicy = "create-drop";

        @Bean(destroyMethod = "close")
        DataSource dataSource() throws IOException {
            licDbPath = File.createTempFile("lic", "db").getAbsolutePath();

            HikariConfig dataSourceConfig = new HikariConfig();
            dataSourceConfig.setDriverClassName("org.h2.Driver");
            dataSourceConfig.setJdbcUrl("jdbc:h2:" + licDbPath);
            dataSourceConfig.setUsername("sa");
            dataSourceConfig.setPassword("");
            return new HikariDataSource(dataSourceConfig);
        }

        @Bean
        LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
            LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
            entityManagerFactoryBean.setDataSource(dataSource);
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
        JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
            JpaTransactionManager transactionManager = new JpaTransactionManager();
            transactionManager.setEntityManagerFactory(entityManagerFactory);
            return transactionManager;
        }

        @Bean
        Mapper mapper() {
            List<String> mappingFiles = Lists.newArrayList("lic_models_mapping.xml");
            return new DozerBeanMapper(mappingFiles);
        }

        @Bean
        public static PropertySourcesPlaceholderConfigurer propertyConfig() {
            return new PropertySourcesPlaceholderConfigurer();
        }
    }
}
