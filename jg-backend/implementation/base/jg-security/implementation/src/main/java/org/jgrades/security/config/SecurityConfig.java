package org.jgrades.security.config;

import org.jgrades.security.auth.RESTAuthenticationFailureHandler;
import org.jgrades.security.auth.RESTAuthenticationSuccesHandler;
import org.jgrades.security.auth.RESTEntryPoint;
import org.jgrades.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableWebSecurity
@EnableJpaRepositories(
        basePackages = {"org.jgrades.security.api.dao"},
        entityManagerFactoryRef = "mainEntityManagerFactory",
        transactionManagerRef = "mainTransactionManager")
@PropertySources({
        @PropertySource(value = "file:${jgrades.application.properties.file}", ignoreResourceNotFound = true)
})
@EnableTransactionManagement
@ComponentScan("org.jgrades.security")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMINISTRATOR");
        auth.inMemoryAuthentication().withUser("student").password("student").roles("STUDENT");
        auth.inMemoryAuthentication().withUser("parent").password("parent").roles("PARENT");
        auth.inMemoryAuthentication().withUser("teacher").password("teacher").roles("TEACHER");
        auth.inMemoryAuthentication().withUser("manager").password("manager").roles("MANAGER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint()).and().
                authorizeRequests().antMatchers("/helloword/private/**").access("hasRole('ROLE_ADMINISTRATOR')").and().formLogin()
                .loginProcessingUrl("/login")
                .usernameParameter("uname")
                .passwordParameter("pswd")
                .and()
                .logout().logoutUrl("/logout");

        http.csrf().disable();
        http.authorizeRequests().antMatchers("/helloword/allow/**").permitAll();
        http.formLogin().successHandler(authenticationSuccessHandler());
        http.formLogin().failureHandler(authenticationFailureHandler());
    }

    @Bean
    public SimpleUrlAuthenticationSuccessHandler authenticationSuccessHandler() {
        return new RESTAuthenticationSuccesHandler();
    }

    @Bean
    public SimpleUrlAuthenticationFailureHandler authenticationFailureHandler() {
        return new RESTAuthenticationFailureHandler();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new RESTEntryPoint();
    }

    @Bean
    public UserDetailsServiceImpl userDetailsService(){
        return new UserDetailsServiceImpl();
    }
}
