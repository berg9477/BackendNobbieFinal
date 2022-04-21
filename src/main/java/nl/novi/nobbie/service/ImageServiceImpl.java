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
    public String saveImg(Long userId, MultipartFile file) throws Exception {
        if (this.upRepos.findById(userId).isPresent()) {
            UserProfile up = this.upRepos.findById(userId).get();
            Image img = new Image();
            try {
                img.content = file.getBytes();
                img.setUser(up);
                repos.save(img);
            } catch (IOException iex) {
                throw new Exception("Error while uploading image...");
            } catch (Exception ex) {
                throw new Exception(ex.getMessage());
            }
            return "Image uploaded";
        } else {
            throw new Exception("No user found for userId: " + userId);
        }
    }

    @Override
    public ImageDto findById(Long userId) throws Exception {
        if (this.upRepos.findById(userId).isPresent()) {
            UserProfile up = this.upRepos.findById(userId).get();
            Image img = up.getPicture();
            if (img == null) {
                throw new Exception("Image not found");
            } else {
                ImageDto imageDto = new ImageDto(img.getId(), up);
                imageDto.setContent(img.getContent());
                return imageDto;
            }
        } else {
            throw new Exception("No user found for userId: " + userId);
        }
    }
}