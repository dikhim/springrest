package org.dikhim.spring.rest.controllers.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

@Configuration
@Profile("!test")
public class ApplicationConfig {
    
    @Bean
    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer properties = new PropertySourcesPlaceholderConfigurer();

        ApplicationHome home = new ApplicationHome(ApplicationHome.class);
        System.out.println("================================== not test");
        Resource resource = new FileSystemResource(home.getDir() + "/config.properties");
        if (!resource.exists()) {
            resource = new ClassPathResource("deployment/default-config.properties");
        }
        properties.setLocation(resource);
        properties.setIgnoreResourceNotFound(false);

        return properties;
    }
}
