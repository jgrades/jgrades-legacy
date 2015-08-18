package org.jgrades.lic.api;

import org.jgrades.lic.api.service.LicenceCheckingService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;

public class LicServiceMockConfig {
    @Bean
    public LicenceCheckingService iicenceCheckingService() {
        return Mockito.mock(LicenceCheckingService.class);
    }
}
