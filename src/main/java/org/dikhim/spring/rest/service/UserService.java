package org.dikhim.spring.rest.service;

import org.dikhim.spring.rest.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById(long id);
    
    void save(User user);

    void update(User user);

    void deleteById(long id);
}
