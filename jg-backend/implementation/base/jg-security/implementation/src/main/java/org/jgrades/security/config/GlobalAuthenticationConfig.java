package org.jgrades.security.config;

import org.jgrades.security.utils.CommonPasswordEncoder;
import org.jgrades.security.utils.OAuthUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;

@Configuration
public class GlobalAuthenticationConfig extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    private OAuthUserDetailService oAuthUserDetailService;

    @Autowired
    private CommonPasswordEncoder commonPasswordEncoder;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(oAuthUserDetailService).passwordEncoder(commonPasswordEncoder);

    }
}
