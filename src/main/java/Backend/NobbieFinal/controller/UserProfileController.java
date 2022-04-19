package Backend.NobbieFinal.controller;


import Backend.NobbieFinal.dto.UserProfileDto;
import Backend.NobbieFinal.model.BabyName;
import Backend.NobbieFinal.model.UserProfile;
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
    private final BabyNameService bnService;

    public UserProfileController(UserProfileService service, BabyNameService bnService) {
        this.service = service;
        this.bnService = bnService;
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
        UserProfileDto uDto = service.getUser(id);
        UserProfile u = new UserProfile(uDto.getUsername(), uDto.getFirstname(), uDto.getLastname(), uDto.getEmailaddress(), uDto.getPassword(), uDto.getUserId(), uDto.getRole(), uDto.getEnabled());
        for (BabyName bn : babyNames) {
            BabyName name = (BabyName) bnService.findNameById(bn.getId());
            u.addBabyNameToList(name);
            u.addBabyNameToList(name);
        }
        service.updateUser(u);
        return new ResponseEntity<>("Name saved to users list", HttpStatus.OK);
    }
    @DeleteMapping("/deleteUser")
    public ResponseEntity<Object> deleteUserById(@RequestParam Long id) {
        service.deleteById(id);
        return new ResponseEntity<>("User has been deleted", HttpStatus.OK);
    }
    @PatchMapping("/resetPassword")
    public ResponseEntity<Object> resetPassword(@RequestParam Long id) {
        UserProfileDto up = service.resetPasswordById(id);
        return new ResponseEntity<>(up, HttpStatus.OK);
    }}