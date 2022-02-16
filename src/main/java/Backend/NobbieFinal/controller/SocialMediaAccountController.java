package Backend.NobbieFinal.controller;


import Backend.NobbieFinal.dto.SocialMediaAccountDto;
import Backend.NobbieFinal.model.SocialMediaAccount;
import Backend.NobbieFinal.repository.SocialMediaAccountRepository;
import Backend.NobbieFinal.service.SocialMediaAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}