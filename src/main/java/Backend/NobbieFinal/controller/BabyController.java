package Backend.NobbieFinal.controller;

import Backend.NobbieFinal.dto.BabyDto;
import Backend.NobbieFinal.service.BabyService;
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

    @GetMapping("/babies")
    public ResponseEntity<Object> getAllBabies() {
        List<BabyDto> babies = service.getAllBabies();
        return new ResponseEntity<>(babies, HttpStatus.OK);
    }

    @GetMapping("/babiesForUser")
    public ResponseEntity<Object> getBabiesById(@RequestParam(required = true)Long id) {
        List<BabyDto> babies = service.getBabiesById(id);
        return new ResponseEntity<>(babies, HttpStatus.OK);
    }


    @PostMapping("/babies")
    public ResponseEntity<Object> createBaby(@Valid @RequestBody BabyDto bdto, BindingResult br) {
        if(br.hasErrors()){
            StringBuilder sb = new StringBuilder();
            for(FieldError de : br.getFieldErrors()){
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