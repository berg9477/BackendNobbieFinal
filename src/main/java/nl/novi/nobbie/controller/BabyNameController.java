package nl.novi.nobbie.controller;

import nl.novi.nobbie.dto.BabyNameDto;
import nl.novi.nobbie.service.BabyNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class BabyNameController {

    @Autowired
    BabyNameService bnService;

    //All Get mappings
    @GetMapping("/babyNames")
    //Retrieves a list of all baby names
    public ResponseEntity<Object> getAllNames() {
        try {
            List<BabyNameDto> bn = bnService.getAllNames();
            return new ResponseEntity<>(bn, HttpStatus.OK);
        } catch (Exception ex) { //Catch any errors while retrieving list of names
            return new ResponseEntity<>("Retrieving list of names failed: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/nameStartsWith")
    //Retrieves a list of names starting with a specific letter
    public ResponseEntity<Object> getNameStartsWith(@RequestParam(required = true) Character ch) {
        try {
            List<BabyNameDto> bn = bnService.getNameStartsWith(ch);
            return new ResponseEntity<>(bn, HttpStatus.OK);
        } catch (Exception ex) { //Catch any errors while retrieving list of names
            return new ResponseEntity<>("ORetrieving list of names failed: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/namesSearch")
    //Retrieves a list of baby names containing a specific keyword
    public ResponseEntity<Object> getNamesContaining(@RequestParam(required = true) String input) {
        try {
            List<BabyNameDto> bn = bnService.getNamesContaining(input);
            return new ResponseEntity<>(bn, HttpStatus.OK);
        } catch (Exception ex) { //Catch any errors while retrieving list of names
            return new ResponseEntity<>("Retrieving list of names failed: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    //All Post mappings
    @PostMapping("/babyNames")
    //Inserts a new baby name to the database
    public ResponseEntity<Object> insertBabyName(@Valid @RequestBody BabyNameDto bnDto, BindingResult br) {
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
                bnService.insertBabyName(bnDto);
                return new ResponseEntity<>("Baby name added!", HttpStatus.CREATED);
            } catch (Exception ex) { //Catch any errors while adding new name
                return new ResponseEntity<>("Inserting baby name failed: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
    }
}
