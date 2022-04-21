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

    //all get mappings
    @GetMapping("/babies")
    public ResponseEntity<Object> getAllBabies() {
        try {
            List<BabyDto> babies = service.getAllBabies();
            return new ResponseEntity<>(babies, HttpStatus.OK);
        } catch (Exception ex) { //Catch any error while retrieving list of all babies
            return new ResponseEntity<>("Ophalen lijst met baby's is niet gelukt: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/babiesForUser")
    public ResponseEntity<Object> getBabiesById(@RequestParam(required = true) Long id) {
        try {
            List<BabyDto> babies = service.getBabiesById(id);
            return new ResponseEntity<>(babies, HttpStatus.OK);
        } catch (Exception ex) { //Catch any errors while retrieving list of baby's
            return new ResponseEntity<>("Ophalen lijst met baby's is mislukt: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    //all post mappings
    @PostMapping("/babies")
    public ResponseEntity<Object> createBaby(@Valid @RequestBody BabyDto bdto, BindingResult br) {
        //first check if the request body is filled in correct (BabyDto)
        if (br.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            for (FieldError de : br.getFieldErrors()) {
                sb.append(de.getDefaultMessage());
                sb.append("\n");
            }
            return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
        } else {
            service.createBaby(bdto);
            return new ResponseEntity<>("Baby aangemaakt!", HttpStatus.CREATED);
        }
    }
}