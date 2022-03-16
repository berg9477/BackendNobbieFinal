package Backend.NobbieFinal.controller;


import Backend.NobbieFinal.dto.UserProfileDto;
import Backend.NobbieFinal.model.BabyName;
import Backend.NobbieFinal.model.UserProfile;
import Backend.NobbieFinal.repository.BabyNameRepository;
import Backend.NobbieFinal.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
public class UserProfileController {
    @Autowired
    UserProfileService service;
    @Autowired
    BabyNameRepository nameRepos;

//All get mappings
    @GetMapping("/users")
    public ResponseEntity<Object> getAllUsers() {
        List<UserProfileDto> up = service.getAllUsers();
        return new ResponseEntity<>(up, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<Object> getUser(@RequestParam Long id) {
        UserProfile up = service.getUser(id);
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
    @PostMapping("/users/{id}/babynames")
    public ResponseEntity<Object> saveBabyNameForUser(@PathVariable(name = "id") Long id, @RequestBody List<BabyName> babyNames) {
        UserProfile u = service.getUser(id);
        for (BabyName bn : babyNames) {
            BabyName name = nameRepos.findById(bn.getId()).get();
            u.addBabyNameToList(name);
        }
        service.updateUser(u);
        return new ResponseEntity<>("Name saved to users list", HttpStatus.OK);
    }
}