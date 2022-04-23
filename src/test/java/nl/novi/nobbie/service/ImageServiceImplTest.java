package nl.novi.nobbie.service;

import nl.novi.nobbie.NobbieFinalApplication;
import nl.novi.nobbie.dto.ImageDto;
import nl.novi.nobbie.model.Image;
import nl.novi.nobbie.model.Role;
import nl.novi.nobbie.model.UserProfile;
import nl.novi.nobbie.repository.ImageRepository;
import nl.novi.nobbie.repository.UserProfileRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ContextConfiguration(classes={NobbieFinalApplication.class})
class ImageServiceImplTest {

    @Autowired
    ImageService service;

    @MockBean
    private ImageRepository repos;
    @MockBean
    private UserProfileRepository upRepos;

    MockMultipartFile file = new MockMultipartFile("file", "foto.png", MediaType.IMAGE_PNG_VALUE, "test".getBytes());
    UserProfile user = new UserProfile("username", "first", "last", "email@grs.nl", "123", 321L, Role.USER, 1);


    @Test
    void saveImg() throws Exception {
        Image img = new Image(file.getBytes(), user);

        when(upRepos.findById(user.getUserId())).thenReturn(java.util.Optional.ofNullable(user));
        when(repos.save(img)).thenReturn(img);

        String test = service.saveImg(user.getUserId(), file);

        assertEquals("Image uploaded", test);
    }

    @Test
    void findByIdFails() throws Exception {
        when(upRepos.findById(user.getUserId())).thenReturn(java.util.Optional.ofNullable(user));
        try {
            ImageDto imageDto = service.findById(user.getUserId());
        } catch (Exception e){
            assertEquals("Image not found", e.getMessage());
        }


    }
}