package Backend.NobbieFinal.controller;


import Backend.NobbieFinal.dto.UserProfileDto;
import Backend.NobbieFinal.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class UserProfileController {
    @Autowired
    UserProfileService service;

    @GetMapping("/users")
    public ResponseEntity<Object> getAllUsers() {
        List<UserProfileDto> up = service.getAllUsers();
        return new ResponseEntity<>(up, HttpStatus.OK);
    }
}