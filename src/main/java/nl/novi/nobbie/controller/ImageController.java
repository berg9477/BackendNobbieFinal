package nl.novi.nobbie.controller;

import nl.novi.nobbie.dto.ImageDto;
import nl.novi.nobbie.dto.UserProfileDto;
import nl.novi.nobbie.service.ImageService;
import nl.novi.nobbie.service.UserProfileService;
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
    UserProfileService upService;

    @PostMapping("/images/{id}")
    public ResponseEntity<Object> upload(@PathVariable Long id, @RequestBody MultipartFile file) {
        UserProfileDto up = upService.getUser(id);
        if(up == null){
            return new ResponseEntity<>("UserId not found", HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(service.saveImg(id, file), HttpStatus.CREATED);
        }
    }

    @GetMapping(value = "/images/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<Object> download(@PathVariable Long id) {
        UserProfileDto up = upService.getUser(id);
        if (up == null) {
            return new ResponseEntity<>("UserId not found", HttpStatus.NOT_FOUND);
        } else {
            ImageDto img = service.findById(id);
            return new ResponseEntity<>(img.content, HttpStatus.OK);
        }
    }
}