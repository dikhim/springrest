package org.dikhim.spring.rest.controllers;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.apache.commons.io.IOUtils;
import org.dikhim.spring.rest.converter.csv.UserCsvConverter;
import org.dikhim.spring.rest.model.User;
import org.dikhim.spring.rest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

@RestController
public class UserImportController {

    private UserService userService;
    
    private UserCsvConverter converter = new UserCsvConverter();

    @Autowired
    public UserImportController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/export")
    public ResponseEntity<Void> exportUsersToCsv(HttpServletResponse response) {
        try {
            String userListCsv = converter.exportList(userService.findAll());
            IOUtils.write(userListCsv,response.getOutputStream(), Charset.forName("utf-8"));
            response.flushBuffer();
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/users/import")
    public ResponseEntity<Void> importUsersFromCsv(@RequestParam MultipartFile file) {
        try {
            String userListCsv = IOUtils.toString(file.getInputStream(),Charset.forName("utf-8"));
            List<User> userList  = converter.importList(userListCsv);
            userList.forEach(u->userService.save(u));
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }    
    
}
