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
public class ImageServiceImpl implements ImageService{
    private final ImageRepository repos;
    private final UserProfileRepository upRepos;

    public ImageServiceImpl(ImageRepository repos, UserProfileRepository upRepos) {
        this.repos = repos;
        this.upRepos = upRepos;
    }

    @Override
    public String saveImg(Long userId, MultipartFile file) {
        UserProfile up = this.upRepos.findById(userId).get();
        Image img = new Image();
        try {
            img.content = file.getBytes();
            img.setUser(up);
            repos.save(img);
        } catch (IOException iex) {
            return "Error while uploading image...";
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return "Image uploaded";
    }

    @Override
    public ImageDto findById(Long userId) throws Exception {
        UserProfile up = this.upRepos.findById(userId).get();
        Image img = up.getPicture();
        if(img == null){
            throw new Exception("image not found");
        } else {
            ImageDto imageDto = new ImageDto(img.getId(), up);
            imageDto.setContent(img.getContent());
            return imageDto;
        }
    }
}