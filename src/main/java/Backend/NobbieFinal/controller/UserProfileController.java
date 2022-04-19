package Backend.NobbieFinal.controller;


import Backend.NobbieFinal.dto.BabyNameDto;
import Backend.NobbieFinal.dto.UserProfileDto;
import Backend.NobbieFinal.model.BabyName;
import Backend.NobbieFinal.service.BabyNameService;
import Backend.NobbieFinal.service.UserProfileService;
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
        List<UserProfileDto> up = service.getAllUsers();
        return new ResponseEntity<>(up, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<Object> getUser(@RequestParam Long id) {
        UserProfileDto up = service.getUser(id);
        return new ResponseEntity<>(up, HttpStatus.OK);
    }

    @GetMapping("/users/{id}/babynames")
    public ResponseEntity<Object> getListOfSavedBabyNames(@PathVariable(name = "id") Long id, @RequestParam Boolean match) {
        List<BabyNameDto> names = service.getSavedNames(id, match);
        return new ResponseEntity<>(names, HttpStatus.OK);
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
    @PostMapping("/users/{id}/babynames")
    public ResponseEntity<Object> saveBabyNameForUser(@PathVariable(name = "id") Long id, @RequestParam Long babyNameId) {
        Boolean match = service.saveBabyName(id, babyNameId); //if user has a connection the name list of this user is checked. If name also there match is true.
        return new ResponseEntity<>(match, HttpStatus.OK);
    }

    //all delete mappings
    @DeleteMapping("/deleteUser")
    public ResponseEntity<Object> deleteUserById(@RequestParam Long id) {
        service.deleteById(id);
        return new ResponseEntity<>("User has been deleted", HttpStatus.OK);
    }

    //all patch mappings
    @PatchMapping("/resetPassword")
    public ResponseEntity<Object> resetPassword(@RequestParam Long id) {
        UserProfileDto up = service.resetPasswordById(id);
        return new ResponseEntity<>(up, HttpStatus.OK);
    }

    @PatchMapping("/connection")
    public ResponseEntity<Object> setConnection(@RequestParam Long id, Long connection) {
        UserProfileDto first = service.setConnection(id, connection); //set connection for user initiating the request
        UserProfileDto second = service.setConnection(connection, id); //also update the connection for the user that's being connected to
       if(first.getUserId() == null || second.getUserId() == null){
           return new ResponseEntity<>("userId or connection userId do not exist", HttpStatus.BAD_REQUEST);
       }
        return new ResponseEntity<>(id + " and " + connection + " are now connected", HttpStatus.OK);
    }
}