package org.dikhim.spring.rest.service;

import org.dikhim.spring.rest.model.User;
import org.dikhim.spring.rest.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service("userService")
public class UserServiceImp implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with the id:%s is not found", id)));

    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteById(long id) {
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new EntityNotFoundException(String.format("User with the id:%s is not found", id));
        }
    }
}
