package org.dikhim.spring.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.dikhim.spring.rest.Application;
import org.dikhim.spring.rest.model.User;
import org.dikhim.spring.rest.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Application.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
public class ITUserController {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper mapper;

    private static long userId;

    @Test
    public void testUserController() throws Exception {
        whenPostUser_thenStatus200UserAdded();
        whenGetUsers_thenReturnJsonArray();
        whenGetUserById_thenReturnUser();
        whenPutUser_thenStatus200();
        whenDeleteUser_thenStatus200UserDeleted();
    }
    
    public void whenPostUser_thenStatus200UserAdded() throws Exception {
        int usersSize = userService.findAll().size();
        User user = createUser();
        String jsonString = mapper.writeValueAsString(user);
        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status()
                        .isOk());

        assertEquals("Size of user list should have been increased", usersSize + 1, userService.findAll().size());
        List<User> users = userService.findAll();
        userId = users.get(users.size() - 1).getId();
    }

    public void whenGetUsers_thenReturnJsonArray() throws Exception {
        mvc.perform(get("/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(userService.findAll().size())));
    }

    public void whenGetUserById_thenReturnUser() throws Exception {
        User user = userService.findById(userId);
        mvc.perform(get("/users/" + userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(user.getId()))
                .andExpect(jsonPath("firstName").value(user.getFirstName()))
                .andExpect(jsonPath("lastName").value(user.getLastName()));
    }

    public void whenPutUser_thenStatus200() throws Exception {
        User user = userService.findById(userId);
        user.setFirstName("Dmitrii");
        user.setLastName("Ivanov");
        String jsonString = mapper.writeValueAsString(user);

        mvc.perform(put("/users/" + userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status()
                        .isOk());
        User updatedUser = userService.findById(userId);
        assertEquals(user.getId(), updatedUser.getId());
        assertEquals(user.getFirstName(), updatedUser.getFirstName());
        assertEquals(user.getLastName(), updatedUser.getLastName());
    }

    public void whenDeleteUser_thenStatus200UserDeleted() throws Exception {
        int usersSize = userService.findAll().size();
        mvc.perform(delete("/users/" + userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status()
                        .isOk());
        assertEquals("Size of user list should have been decreased", usersSize -1, userService.findAll().size());
    }

    private static User createUser() {
        User user = new User();
        user.setFirstName("Serhii");
        user.setLastName("Muslanov");
        return user;
    }
}
