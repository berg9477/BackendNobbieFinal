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

    //All Get mappings
    @GetMapping(value = "/images/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    //Download an image for a specific id
    public ResponseEntity<Object> download(@PathVariable Long id) {
        try {
            ImageDto img = service.findById(id);
            return new ResponseEntity<>(img.content, HttpStatus.OK);
        } catch (Exception ex) { //Catch any errors while downloading image
            return new ResponseEntity<>("No image found: " + ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    //All Post mappings
    @PostMapping("/images/{id}")
    //Upload an image for a specific user
    public ResponseEntity<Object> upload(@PathVariable Long id, @RequestBody MultipartFile file) {
        try {
            String save = service.saveImg(id, file);
            return new ResponseEntity<>(save, HttpStatus.CREATED);
        } catch (Exception ex) { //Catch any errors while uploading image
            return new ResponseEntity<>("Saving of image failed: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }


}