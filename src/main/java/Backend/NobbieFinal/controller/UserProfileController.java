package Backend.NobbieFinal.controller;


import Backend.NobbieFinal.dto.BabyNameDto;
import Backend.NobbieFinal.dto.UserProfileDto;
import Backend.NobbieFinal.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;


@RestController
public class UserProfileController {
    @Autowired
    UserProfileService service;
//All get mappings
    @GetMapping("/users")
    public ResponseEntity<Object> getAllUsers() {
        List<UserProfileDto> up = service.getAllUsers();
        return new ResponseEntity<>(up, HttpStatus.OK);
    }
//All post mappings
    @PostMapping("/createUser")
    public ResponseEntity<Object> createNewUser(@Valid @RequestBody UserProfileDto updto, BindingResult br) {
        if(br.hasErrors()){
            StringBuilder sb = new StringBuilder();
            for(FieldError de : br.getFieldErrors()){
                sb.append(de.getDefaultMessage());
                sb.append("\n");
            }
            return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);

        } else {
            service.createNewUser(updto);
            return new ResponseEntity<>("Profiel aangemaakt!", HttpStatus.CREATED);
        }
    }
}