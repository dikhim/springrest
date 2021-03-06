package org.dikhim.spring.rest.controllers;

import org.dikhim.spring.rest.model.User;
import org.dikhim.spring.rest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public List<User> list() {
        return userService.findAll();
    }

    @PostMapping("")
    public ResponseEntity<Void>  post(@RequestBody @Valid User user) {
        userService.save(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") long id) {
        return userService.findById(id);
    }

    @PutMapping("/{id}")
    public User put(@RequestBody @Valid User user, @PathVariable("id") long id) {
        user.setId(id);
        userService.update(user);
        return user;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }   
}
