package Backend.NobbieFinal.controller;


import Backend.NobbieFinal.model.SocialMediaAccount;
import Backend.NobbieFinal.repository.SocialMediaAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SocialMediaAccountController {
    @Autowired
    SocialMediaAccountRepository repos;

    @GetMapping("/socialMediaAccounts")
    public ResponseEntity<Object> getAllNames() {
        List<SocialMediaAccount> sma = repos.findAll();
        return new ResponseEntity<>(sma, HttpStatus.OK);
    }
}