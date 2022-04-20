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

    //All get mappings
    @GetMapping("/babynames")
    public ResponseEntity<Object> getAllNames() {
        try {
            List<BabyNameDto> bn = bnService.getAllNames();
            return new ResponseEntity<>(bn, HttpStatus.OK);
        } catch (Exception ex) { //Catch any errors while retrieving list of names
            return new ResponseEntity<>("Ophalen lijst met namen is mislukt: " + ex.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/nameStartsWith")
    public ResponseEntity<Object> getNameStartsWith(@RequestParam(required = true) Character ch) {
        try {
            List<BabyNameDto> bn = bnService.getNameStartsWith(ch);
            return new ResponseEntity<>(bn, HttpStatus.OK);
        } catch (Exception ex) { //Catch any errors while retrieving list of names
            return new ResponseEntity<>("Ophalen lijst met namen is mislukt: " + ex.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/namesSearch")
    public ResponseEntity<Object> getNamesContaining(@RequestParam(required = true) String input) {
        try {
            List<BabyNameDto> bn = bnService.getNamesContaining(input);
            return new ResponseEntity<>(bn, HttpStatus.OK);
        } catch (Exception ex) { //Catch any errors while retrieving list of names
            return new ResponseEntity<>("Ophalen lijst met namen is mislukt: " + ex.getMessage(), HttpStatus.CONFLICT);
        }
    }

    //All post mappings
    @PostMapping("/babynames")
    public ResponseEntity<Object> insertBabyName(@Valid @RequestBody BabyNameDto bndto, BindingResult br) {
        if (br.hasErrors()) { //first check if the request body is filled in correct (BabyNameDto)
            StringBuilder sb = new StringBuilder();
            for (FieldError de : br.getFieldErrors()) {
                sb.append(de.getDefaultMessage());
                sb.append("\n");
            }
            return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);

        } else {
            bnService.insertBabyName(bndto);
            return new ResponseEntity<>("Baby naam toegevoegd!", HttpStatus.CREATED);
        }
    }

}
