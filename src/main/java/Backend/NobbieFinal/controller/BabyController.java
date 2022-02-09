package Backend.NobbieFinal.controller;

import Backend.NobbieFinal.model.Baby;
import Backend.NobbieFinal.repository.BabyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class BabyController {
    @Autowired
    BabyRepository repos;

    @GetMapping("/babies")
    public ResponseEntity<Object> getAllBabies() {
        List<Baby> babies = repos.findAll();
        return new ResponseEntity<>(babies, HttpStatus.OK);
    }
}