package org.dikhim.spring.rest.converter.csv;

import org.dikhim.spring.rest.model.User;
import org.junit.Before;
import org.junit.Test;

import javax.validation.constraints.Max;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class UserCsvConverterTest {

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
    public void exportList() throws Exception {
        UserCsvConverter converter = new UserCsvConverter();
        String csvString = converter.exportList(users);
        assertEquals("Failed to export user list", csvString, csvUsers);
    }

    @Test
    public void importList() throws Exception {
        UserCsvConverter converter = new UserCsvConverter();
        List<User> importList = converter.importList(csvUsers);
        assertEquals("Failed to import user list from csv", importList, users);
    }
}