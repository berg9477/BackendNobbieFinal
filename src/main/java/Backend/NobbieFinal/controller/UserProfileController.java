package Backend.NobbieFinal.controller;


import Backend.NobbieFinal.model.UserProfile;
import Backend.NobbieFinal.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class UserProfileController {
    @Autowired
    UserProfileRepository repos;

    @GetMapping("/users")
    public ResponseEntity<Object> getAllUsers() {
        List<UserProfile> up = repos.findAll();
        return new ResponseEntity<>(up, HttpStatus.OK);
    }
}