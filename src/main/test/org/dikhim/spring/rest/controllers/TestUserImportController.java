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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserImportController.class)
public class TestUserImportController {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    private List<User> users;
    private String csvUsers = "\"age\",\"firstName\",\"id\",\"lastName\"\n" +
            "\"11\",\"Serhii\",\"1\",\"Muslanov\"\n" +
            "\"22\",\"Vladimir\",\"2\",\"Ivanov\"\n" +
            "\"33\",\"Dmitrii\",\"3\",\"Petrov\"\n";

    @Before
    public void setUp() {
        users = new ArrayList<>();
        User user = new User();
        user.setId(1);
        user.setFirstName("Serhii");
        user.setLastName("Muslanov");
        user.setAge(11);
        users.add(user);

        user = new User();
        user.setId(2);
        user.setFirstName("Vladimir");
        user.setLastName("Ivanov");
        user.setAge(22);
        users.add(user);

        user = new User();
        user.setId(3);
        user.setFirstName("Dmitrii");
        user.setLastName("Petrov");
        user.setAge(33);
        users.add(user);
    }
    
    @Test
    public void exportUsersToCsv() throws Exception {
        mvc.perform(get("/users/export").contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk());
    }

    @Test
    public void importUsersFromCsv() throws Exception {        
        MockMultipartFile mockMultipartFile = new MockMultipartFile("users", "users.csv", "multipart/form-data", csvUsers.getBytes());
        mvc.perform(MockMvcRequestBuilders.multipart("/users/import")
                .file(mockMultipartFile))
                .andExpect(status().isOk());

    }
}