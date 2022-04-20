package nl.novi.nobbie.controller;

import nl.novi.nobbie.dto.SocialMediaAccountDto;
import nl.novi.nobbie.dto.UserProfileDto;
import nl.novi.nobbie.model.MediaType;
import nl.novi.nobbie.service.SocialMediaAccountService;
import nl.novi.nobbie.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class SocialMediaAccountController {
    @Autowired
    SocialMediaAccountService service;
    UserProfileService upService;


    @GetMapping("/socialMediaAccounts")
    public ResponseEntity<Object> getAllAccounts() {
        try {
            List<SocialMediaAccountDto> sma = service.getAllAccounts();
            return new ResponseEntity<>(sma, HttpStatus.OK);
        } catch (Exception ex) { //Catch any errors while retrieving list of social media accounts
            return new ResponseEntity<>("Ophalen lijst met social media accounts is mislukt: " + ex.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/socialMediaAccounts")
    public ResponseEntity<Object> createSMA(@Valid @RequestBody SocialMediaAccountDto SMAdto, BindingResult br) {
        //first check if the request body is filled in correct (SocialMediaAccountDto)
        if (br.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            for (FieldError de : br.getFieldErrors()) {
                sb.append(de.getDefaultMessage());
                sb.append("\n");
            }
            return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
        } else {
            service.createSMA(SMAdto);
            return new ResponseEntity<>("Social media account aangemaakt!", HttpStatus.CREATED);
        }
    }

    @GetMapping("/socialMediaMessage")
    public ResponseEntity<Object> getSMAMessage(@RequestParam MediaType mediaType, Long id) {
        //first check if userId exists
        UserProfileDto up = upService.getUser(id);
        if (up == null) {
            return new ResponseEntity<>("UserId niet gevonden: " + id, HttpStatus.NOT_FOUND);
        } else {
            try {
                String message = service.getSMAMessage(mediaType, id);
                return new ResponseEntity<>(message, HttpStatus.OK);
            } catch (Exception ex) { //Catch any errors while generating social media message
                return new ResponseEntity<>("Genereren social media bericht is mislukt: " + ex.getMessage(), HttpStatus.CONFLICT);
            }
        }
    }
}