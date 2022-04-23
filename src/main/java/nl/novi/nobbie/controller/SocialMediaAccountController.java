package nl.novi.nobbie.controller;

import nl.novi.nobbie.dto.SocialMediaAccountDto;
import nl.novi.nobbie.model.MediaType;
import nl.novi.nobbie.service.SocialMediaAccountService;
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

    //all Get mappings
    @GetMapping("/socialMediaAccounts")
    //Gets a list of all social media accounts
    public ResponseEntity<Object> getAllAccounts() {
        try {
            List<SocialMediaAccountDto> sma = service.getAllAccounts();
            return new ResponseEntity<>(sma, HttpStatus.OK);
        } catch (Exception ex) { //Catch any errors while retrieving list of social media accounts
            return new ResponseEntity<>("Retrieving list of social media accounts failed: " + ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/socialMediaMessage")
    //Generates a message about the pregnancy status to use on a social media platform for a specific user
    public ResponseEntity<Object> getSMAMessage(@RequestParam MediaType mediaType, Long id) {
        try {
            String message = service.getSMAMessage(mediaType, id);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Exception ex) { //Catch any errors while generating social media message
            return new ResponseEntity<>("Generating social media message failed: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    //All Post mappings
    @PostMapping("/socialMediaAccounts")
    //Inserts a social media account into a specific user profile
    public ResponseEntity<Object> createSMA(@Valid @RequestBody SocialMediaAccountDto SMADto, BindingResult br) {
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
                service.createSMA(SMADto);
                return new ResponseEntity<>("Social media account created!", HttpStatus.CREATED);
            } catch (Exception ex) { //Catch any errors while creating social media account
                return new ResponseEntity<>("creating social media account failed: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
    }


}