package org.jgrades.rest.lic;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.jgrades.data.api.dao.UserRepository;
import org.jgrades.data.api.service.DataSourceService;
import org.jgrades.lic.api.service.LicenceCheckingService;
import org.jgrades.lic.api.service.LicenceManagingService;
import org.jgrades.security.api.service.PasswordMgntService;
import org.jgrades.security.api.service.PasswordPolicyService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class LicMockConfig extends WebMvcConfigurerAdapter {
    @Bean
    public LicenceManagingService licenceManagingService() {
        return Mockito.mock(LicenceManagingService.class);
    }

    @Bean
    public LicenceCheckingService licenceCheckingService() {
        return Mockito.mock(LicenceCheckingService.class);
    }

    @Bean(name = "resolverMock")
    @Primary
    public IncomingFilesNameResolver incomingFilesNameResolver() {
        return Mockito.mock(IncomingFilesNameResolver.class);
    }

    @Bean
    public DataSourceService dataSourceService() {
        return Mockito.mock(DataSourceService.class);
    }

    @Bean(name = "userDetailsService")
    @Primary
    public UserDetailsService userDetailsService() {
        return Mockito.mock(UserDetailsService.class);
    }

    @Bean
    @Primary
    public org.apache.commons.configuration.Configuration appConfiguration() {
        return new PropertiesConfiguration();
    }

    @Bean
    @Primary
    public PasswordMgntService passwordMgntService() {
        return Mockito.mock(PasswordMgntService.class);
    }

    @Bean
    @Primary
    public PasswordPolicyService passwordPolicyService() {
        return Mockito.mock(PasswordPolicyService.class);
    }

    @Bean
    @Primary
    public UserRepository userRepository() {
        return Mockito.mock(UserRepository.class);
    }
}
