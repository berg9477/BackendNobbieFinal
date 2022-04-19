package Backend.NobbieFinal.controller;

import Backend.NobbieFinal.dto.SocialMediaAccountDto;
import Backend.NobbieFinal.model.MediaType;
import Backend.NobbieFinal.model.SocialMediaAccount;
import Backend.NobbieFinal.service.SocialMediaAccountService;
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

    @GetMapping("/socialMediaAccounts")
    public ResponseEntity<Object> getAllAccounts() {
        List<SocialMediaAccountDto> sma = service.getAllAccounts();
        return new ResponseEntity<>(sma, HttpStatus.OK);
    }

    @PostMapping("/socialMediaAccounts")
    public ResponseEntity<Object> createSMA(@Valid @RequestBody SocialMediaAccountDto SMAdto, BindingResult br) {
        if(br.hasErrors()){
            StringBuilder sb = new StringBuilder();
            for(FieldError de : br.getFieldErrors()){
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
        String message = service.getSMAMessage(mediaType, id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}