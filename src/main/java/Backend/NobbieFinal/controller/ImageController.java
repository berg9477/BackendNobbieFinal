package Backend.NobbieFinal.controller;

import Backend.NobbieFinal.dto.ImageDto;
import Backend.NobbieFinal.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class ImageController {
    @Autowired
    ImageService service;

    @PostMapping("/images")
    public String upload(@RequestBody MultipartFile file) {
        ImageDto img = new ImageDto(1L);
        try {
            img.content = file.getBytes();
        } catch (IOException iex) {
            return "Error while uploading image...";
        } catch (Exception ex) {
            return ex.getMessage();
        }

        service.saveImg(img);
        return "Image uploaded";
    }

    @GetMapping(value = "/images/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody
    byte[] download(@PathVariable Long id) {
        ImageDto img = service.findById(id);
        return img.content;
    }
}