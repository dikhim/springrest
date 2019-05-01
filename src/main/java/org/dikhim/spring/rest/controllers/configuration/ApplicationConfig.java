package org.dikhim.spring.rest.controllers.configuration;

import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class Config {
    
    @Bean
    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer properties = new PropertySourcesPlaceholderConfigurer();

        ApplicationHome home = new ApplicationHome(ApplicationHome.class);
        System.out.println(home.getDir());
        System.out.println(home.getSource());
        
        properties.setLocation(new FileSystemResource(home.getDir() + "/config.properties"));
        properties.setIgnoreResourceNotFound(false);

        return properties;
    }
    
}
