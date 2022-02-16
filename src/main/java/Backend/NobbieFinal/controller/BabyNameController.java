package Backend.NobbieFinal.controller;

import Backend.NobbieFinal.dto.BabyNameDto;
import Backend.NobbieFinal.model.BabyName;
import Backend.NobbieFinal.repository.BabyNameRepository;
import Backend.NobbieFinal.service.BabyNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BabyNameController {
    @Autowired
    BabyNameService service;

    @GetMapping("/babynames")
    public ResponseEntity<Object> getAllNames() {
        List<BabyNameDto> bn = service.getAllNames();
        return new ResponseEntity<>(bn, HttpStatus.OK);
    }
}