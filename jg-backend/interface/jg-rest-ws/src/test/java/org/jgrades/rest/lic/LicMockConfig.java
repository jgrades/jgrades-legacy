package org.jgrades.rest.lic;

import org.jgrades.data.api.service.DataSourceService;
import org.jgrades.lic.api.service.LicenceCheckingService;
import org.jgrades.lic.api.service.LicenceManagingService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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


}
