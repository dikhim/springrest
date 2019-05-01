package org.dikhim.spring.rest.controllers.configuration;

import org.dikhim.spring.rest.model.User;
import org.dikhim.spring.rest.persistence.UserRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;

import javax.annotation.PostConstruct;

@Configuration
@Profile("test")
public class TestProfileConfig {
    private final UserRepository userRepository;

    @Autowired
    public TestProfileConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    InitializingBean initializingBean() {
        return () -> {
            userRepository.save(new User("Serhii", "Muslanov",25));
            userRepository.save(new User("Oleg", "Petrov",34));
            userRepository.save(new User("Olga", "Ivanovna",22));
            userRepository.save(new User("Petr", "Mubarev",66));
        };
    }

}
