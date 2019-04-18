package org.dikhim.spring.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.dikhim.spring.rest.model.User;
import org.dikhim.spring.rest.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class TestUserController {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper mapper;


    private User user;
    private List<User> users;

    @Before
    public void setUp() {
        this.users = new ArrayList<>();
        User user = new User();
        user.setId(1);
        user.setFirstName("Serhii");
        user.setLastName("Muslanov");
        user.setAge(12);
        this.user = user;
        users.add(user);

        user = new User();
        user.setId(2);
        user.setFirstName("Vladimir");
        user.setLastName("Ivanov");
        user.setAge(13);
        users.add(user);
    }

    @Test
    public void givenUsers_whenGetUsers_thenReturnJsonArray() throws Exception {
        given(userService.findAll()).willReturn(users);

        mvc.perform(get("/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName").value(user.getFirstName()))
                .andExpect(jsonPath("$[0].lastName").value(user.getLastName()))
                .andExpect(jsonPath("$[0].age").value(user.getAge()));
    }

    @Test
    public void whenPostUser_thenOk() throws Exception {
        String jsonString = mapper.writeValueAsString(user);
        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status()
                        .isOk());
    }

    @Test
    public void givenUser_whenGetUserById_thenReturnJsonObject() throws Exception {
        given(userService.findById(1)).willReturn(user);
        
        mvc.perform(get("/users/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("firstName").value(user.getFirstName()))
                .andExpect(jsonPath("lastName").value(user.getLastName()))
                .andExpect(jsonPath("age").value(user.getAge()));
    }

    @Test
    public void whenPutUser_thenOk() throws Exception {
        String jsonString = mapper.writeValueAsString(user);
        
        mvc.perform(put("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status()
                        .isOk());
    }

    @Test
    public void whenDeleteUser_thenOk() throws Exception {
        mvc.perform(delete("/users/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status()
                        .isOk());
    }
    
    @Test 
    public void whenPostNotValidUser_thenBadRequest() throws Exception{
        User notValidUser = new User();
        notValidUser.setFirstName("Aa");
        notValidUser.setLastName("Too long name Too long name Too long name");
        notValidUser.setAge(101);

        String jsonString = mapper.writeValueAsString(notValidUser);

        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status()
                        .isBadRequest());
    }
}