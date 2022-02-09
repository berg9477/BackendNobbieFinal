package Backend.NobbieFinal.controller;

import Backend.NobbieFinal.model.Baby;
import Backend.NobbieFinal.repository.BabyRepository;
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
    BabyRepository repos;

    @GetMapping("/babies")
    public ResponseEntity<Object> getAllBabies() {
        List<Baby> babies = repos.findAll();
        return new ResponseEntity<>(babies, HttpStatus.OK);
    }

    @PostMapping("/babies")
    public ResponseEntity<Object> createBaby(@Valid @RequestBody Baby b, BindingResult br) {
        if(br.hasErrors()){
            StringBuilder sb = new StringBuilder();
            for(FieldError de : br.getFieldErrors()){
                sb.append(de.getDefaultMessage());
                sb.append("\n");
            }
            return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);

        } else {
            repos.save(b);
            return new ResponseEntity<>("Baby aangemaakt!", HttpStatus.CREATED);
        }
    }

}