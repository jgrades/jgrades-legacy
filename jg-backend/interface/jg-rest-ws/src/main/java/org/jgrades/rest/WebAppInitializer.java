package org.jgrades.rest;

import org.jgrades.lic.config.LicConfig;
import org.jgrades.property.ApplicationPropertiesConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class WebAppInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(ApplicationPropertiesConfig.class, LicConfig.class);
        rootContext.refresh();

        servletContext.addListener(new ContextLoaderListener(rootContext));
        servletContext.setInitParameter("defaultHtmlEscape", "true");

        AnnotationConfigWebApplicationContext mvcContext = new AnnotationConfigWebApplicationContext();
        mvcContext.register(ApplicationPropertiesConfig.class, RestConfig.class);

        ServletRegistration.Dynamic appServlet = servletContext.addServlet("jgRestServlet", new DispatcherServlet(mvcContext));
        appServlet.setLoadOnStartup(1);
        appServlet.addMapping("/");
        appServlet.setMultipartConfig(new MultipartConfigElement(mvcContext.getEnvironment().getProperty("rest.lic.path"), 1024 * 1024 * 5, 1024 * 1024 * 5 * 5, 1024 * 1024));
    }
}
