package nl.novi.nobbie.controller;


import nl.novi.nobbie.dto.BabyNameDto;
import nl.novi.nobbie.dto.UserProfileDto;
import nl.novi.nobbie.model.UserProfile;
import nl.novi.nobbie.service.UserProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
public class UserProfileController {

    private final UserProfileService service;

    public UserProfileController(UserProfileService service) {
        this.service = service;
    }

    //All get mappings
    @GetMapping("/users")
    //retrieves list of all users
    public ResponseEntity<Object> getAllUsers() {
        try {
            List<UserProfileDto> up = service.getAllUsers();
            return new ResponseEntity<>(up, HttpStatus.OK);
        } catch (Exception ex) { //Catch any error while retrieving list of all users
            return new ResponseEntity<>("Retrieving list of users failed: " + ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user")
    //retrieves user profile for specific user
    public ResponseEntity<Object> getUser(@RequestParam Long id) {
        try {
            UserProfileDto up = service.getUser(id);
            return new ResponseEntity<>(up, HttpStatus.OK);
        } catch (Exception ex) { //Catch any error while retrieving user for id
            return new ResponseEntity<>("Retrieving user failed: " + ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/users/{id}/babyNames")
    //Retrieves the list of saved baby names for a specific user (with filtering option (true/false) on names with match with connection)
    public ResponseEntity<Object> getListOfSavedBabyNames(@PathVariable(name = "id") Long id, @RequestParam Boolean match) {
        try {
            List<BabyNameDto> names = service.getSavedNames(id, match);
            return new ResponseEntity<>(names, HttpStatus.OK);
        } catch (Exception ex) { //Catch any error while retrieving user for id
            return new ResponseEntity<>("Retrieving list of baby names for user failed: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    //All post mappings
    @PostMapping("/user")
    //Creates a new UserProfile
    public ResponseEntity<Object> createNewUser(@Valid @RequestBody UserProfileDto upDto, BindingResult br) {
        //first check if the request body is filled in correct and has no validation errors
        if (br.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            //there can be more than one validation error, so a string with a new line per error is being created
            for (FieldError de : br.getFieldErrors()) {
                sb.append(de.getDefaultMessage());
                sb.append("\n");
            }
            return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
        } else {
            try {
                UserProfile up = service.createNewUser(upDto);
                return new ResponseEntity<>(up, HttpStatus.CREATED);
            } catch (Exception ex) { //Catch any errors while creating new user
                return new ResponseEntity<>("Creating user failed: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
    }

    @PostMapping("/users/{id}/babyNames")
    //saves a name in the user profile of the user in the savedNamesList.
    //If the user has a connection, a check will be done of this user also saved this name. The result is a boolean
    public ResponseEntity<Object> saveBabyNameForUser(@PathVariable(name = "id") Long id, @RequestParam Long
            babyNameId) {
        try {
            Boolean match = service.saveBabyName(id, babyNameId);
            return new ResponseEntity<>(match, HttpStatus.OK);
        } catch (Exception ex) { //Catch any errors while saving names
            return new ResponseEntity<>("Saving name for user failed: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    //all delete mappings
    @DeleteMapping("/user")
    //Deletes a user profile for a specific user
    public ResponseEntity<Object> deleteUserById(@RequestParam Long id) {
        try {
            service.deleteById(id);
            return new ResponseEntity<>("User is deleted", HttpStatus.OK);
        } catch (Exception ex) { //Catch any errors while deleting user profile
            return new ResponseEntity<>("Deleting user failed: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    //all patch mappings
    @PatchMapping("/resetPassword")
    //Resets the password for a specific user to a random value.
    //New password is saved with encryption to the database.
    public ResponseEntity<Object> resetPassword(@RequestParam Long id) {
        try {
            UserProfileDto newPW = service.resetPasswordById(id);
            return new ResponseEntity<>(newPW, HttpStatus.OK);
        } catch (Exception ex) { //Catch any errors while resetting password
            return new ResponseEntity<>("Password reset failed: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/connection")
    //Updates two user profiles by setting a connection
    public ResponseEntity<Object> setConnection(@RequestParam Long id, Long connection) {
        try {
            UserProfileDto first = service.setConnection(id, connection); //set connection for user initiating the request
            UserProfileDto second = service.setConnection(connection, id); //also update the connection for the user that's being connected to
            if (first.getUserId() == null || second.getUserId() == null) {
                return new ResponseEntity<>("UserId or userId to connect with failed", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(id + " and " + connection + " are now connected", HttpStatus.OK);
        } catch (Exception ex) { //Catch any errors while setting connection
            return new ResponseEntity<>("Connecting users has failed: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
