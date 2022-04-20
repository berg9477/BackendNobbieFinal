package nl.novi.nobbie.controller;

import nl.novi.nobbie.dto.ImageDto;
import nl.novi.nobbie.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
public class ImageController {
    @Autowired
    ImageService service;

    @PostMapping("/images/{id}")
    public ResponseEntity<Object> upload(@PathVariable Long id, @RequestBody MultipartFile file) {
        try {
            String save = service.saveImg(id, file);
            return new ResponseEntity<>(save, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>("Opslaan afbeelding is mislukt: " + ex.getMessage(), HttpStatus.CONFLICT);
        }

    }

    @GetMapping(value = "/images/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<Object> download(@PathVariable Long id) {
        try {
            ImageDto img = service.findById(id);
            return new ResponseEntity<>(img.content, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Geen afbeelding gevonden: " + ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}