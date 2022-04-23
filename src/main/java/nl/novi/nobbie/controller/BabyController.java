package nl.novi.nobbie.controller;

import nl.novi.nobbie.dto.BabyDto;
import nl.novi.nobbie.service.BabyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class BabyController {

    @Autowired
    BabyService service;

    //all Get mappings
    @GetMapping("/babies")
    //Retrieves a list of all registered babies in the database
    public ResponseEntity<Object> getAllBabies() {
        try {
            List<BabyDto> babies = service.getAllBabies();
            return new ResponseEntity<>(babies, HttpStatus.OK);
        } catch (Exception ex) { //Catch any error while retrieving list of all babies
            return new ResponseEntity<>("Retrieving list of babies failed: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/babiesForUser")
    //Retrieves a list of all babies of a specific user
    public ResponseEntity<Object> getBabiesById(@RequestParam(required = true) Long id) {
        try {
            List<BabyDto> babies = service.getBabiesById(id);
            return new ResponseEntity<>(babies, HttpStatus.OK);
        } catch (Exception ex) { //Catch any errors while retrieving list of babies
            return new ResponseEntity<>("Retrieving list of babies failed: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    //all Post mappings
    @PostMapping("/babies")
    public ResponseEntity<Object> createBaby(@Valid @RequestBody BabyDto bDto, BindingResult br) {
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
            service.createBaby(bDto);
            return new ResponseEntity<>("Baby created!", HttpStatus.CREATED);
        }
    }
}