package nl.novi.nobbie.controller;


import nl.novi.nobbie.dto.BabyNameDto;
import nl.novi.nobbie.dto.UserProfileDto;
import nl.novi.nobbie.model.UserProfile;
import nl.novi.nobbie.service.BabyNameService;
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

    public UserProfileController(UserProfileService service, BabyNameService bnService) {
        this.service = service;
    }

    //All get mappings
    @GetMapping("/users")
    public ResponseEntity<Object> getAllUsers() {
        try {
            List<UserProfileDto> up = service.getAllUsers();
            return new ResponseEntity<>(up, HttpStatus.OK);
        } catch (Exception ex) { //Catch any error while retrieving list of all users
            return new ResponseEntity<>("Ophalen lijst met users is niet gelukt: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user")
    public ResponseEntity<Object> getUser(@RequestParam Long id) {
        try {
            UserProfileDto up = service.getUser(id);
            return new ResponseEntity<>(up, HttpStatus.OK);
        } catch (Exception ex) { //Catch any error while retrieving user for Id
            return new ResponseEntity<>("Ophalen user is niet gelukt: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/{id}/babyNames")
    public ResponseEntity<Object> getListOfSavedBabyNames(@PathVariable(name = "id") Long id, @RequestParam Boolean match) {
        try {
            List<BabyNameDto> names = service.getSavedNames(id, match);
            return new ResponseEntity<>(names, HttpStatus.OK);
        } catch (Exception ex) { //Catch any error while retrieving user for Id
            return new ResponseEntity<>("Ophalen lijst met baby namen voor user is niet gelukt: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    //All post mappings
    @PostMapping("/createUser")
    public ResponseEntity<Object> createNewUser(@Valid @RequestBody UserProfileDto upDto, BindingResult br) throws Exception {
        //first check if the request body is filled in correct (UserProfileDto)
        if (br.hasErrors()) {
            StringBuilder sb = new StringBuilder();
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
                return new ResponseEntity<>("Aanmaken user is mislukt: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
    }

    @PostMapping("/users/{id}/babyNames")
    public ResponseEntity<Object> saveBabyNameForUser(@PathVariable(name = "id") Long id, @RequestParam Long
            babyNameId) {
        try {
            Boolean match = service.saveBabyName(id, babyNameId); //if user has a connection the name list of this user is checked. If name also there match is true.
            return new ResponseEntity<>(match, HttpStatus.OK);
        } catch (Exception ex) { //Catch any errors while retrieving list of baby's
            return new ResponseEntity<>("Ophalen lijst met namen is mislukt: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    //all delete mappings
    @DeleteMapping("/deleteUser")
    public ResponseEntity<Object> deleteUserById(@RequestParam Long id) {
        try {
            service.deleteById(id);
            return new ResponseEntity<>("User is verwijderd", HttpStatus.OK);
        } catch (Exception ex) { //Catch any errors while retrieving list of baby's
            return new ResponseEntity<>("Verwijderen van gebruiker is mislukt: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    //all patch mappings
    @PatchMapping("/resetPassword")
    public ResponseEntity<Object> resetPassword(@RequestParam Long id) {
        try {
            UserProfileDto newPW = service.resetPasswordById(id);
            return new ResponseEntity<>(newPW, HttpStatus.OK);
        } catch (Exception ex) { //Catch any errors while retrieving list of baby's
            return new ResponseEntity<>("Resetten van het password is mislukt: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/connection")
    public ResponseEntity<Object> setConnection(@RequestParam Long id, Long connection) {
        try {
            UserProfileDto first = service.setConnection(id, connection); //set connection for user initiating the request
            UserProfileDto second = service.setConnection(connection, id); //also update the connection for the user that's being connected to
            if (first.getUserId() == null || second.getUserId() == null) {
                return new ResponseEntity<>("userId of te koppelen userId bestaat niet", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(id + " en " + connection + " zijn nu gekoppeld", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Het koppelen van de users is mislukt: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
