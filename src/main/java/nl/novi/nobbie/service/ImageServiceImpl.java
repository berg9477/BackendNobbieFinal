package nl.novi.nobbie.service;

import nl.novi.nobbie.dto.ImageDto;
import nl.novi.nobbie.model.Image;
import nl.novi.nobbie.model.UserProfile;
import nl.novi.nobbie.repository.ImageRepository;
import nl.novi.nobbie.repository.UserProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageServiceImpl implements ImageService {
    private final ImageRepository repos;
    private final UserProfileRepository upRepos;

    public ImageServiceImpl(ImageRepository repos, UserProfileRepository upRepos) {
        this.repos = repos;
        this.upRepos = upRepos;
    }

    @Override
    //Uploading an image to repository
    public String saveImg(Long userId, MultipartFile file) throws Exception {
        //Check if user can be found
        if (this.upRepos.findById(userId).isPresent()) {
            UserProfile up = this.upRepos.findById(userId).get();
            Image img = new Image();
            try { //Saving the image
                img.content = file.getBytes();
                img.setUser(up);
                up.setPicture(img);
                repos.save(img);
                upRepos.save(up);
            } catch (IOException iex) {
                throw new Exception("Error while uploading image...");
            } catch (Exception ex) {
                throw new Exception(ex.getMessage());
            }
            return "Image uploaded"; //message send if upload was successful
        } else {
            throw new Exception("No user found for userId: " + userId);
        }
    }

    @Override
    //Download image from repository for specific user
    public ImageDto findById(Long userId) throws Exception {
        //Check if user can be found
        if (this.upRepos.findById(userId).isPresent()) {
            UserProfile up = this.upRepos.findById(userId).get();
            Image img = up.getPicture();
            if (img == null) { //check if image was found
                throw new Exception("Image not found");
            } else {
                //Map to Dto object
                ImageDto imageDto = new ImageDto(img.getId(), up);
                imageDto.setContent(img.getContent());
                return imageDto;
            }
        } else {
            throw new Exception("No user found for userId: " + userId);
        }
    }
}