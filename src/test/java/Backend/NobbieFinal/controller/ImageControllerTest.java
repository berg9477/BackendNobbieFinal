package Backend.NobbieFinal.controller;

import Backend.NobbieFinal.NobbieFinalApplication;
import Backend.NobbieFinal.dto.ImageDto;
import Backend.NobbieFinal.model.Image;
import Backend.NobbieFinal.service.*;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.sql.DataSource;


@WebMvcTest
@ContextConfiguration(classes={NobbieFinalApplication.class})
class ImageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImageService service;
    @MockBean
    private BabyService babyservice;
    @MockBean
    private BabyNameService babyNameService;
    @MockBean
    private SocialMediaAccountService socialMediaAccountService;
    @MockBean
    private UserProfileService userProfileService;
    @MockBean
    private DataSource dataSource;

    @Test
    @WithMockUser(username="admin",authorities={"0"})
    public void upload() throws Exception {

        //insert testdata
        ImageDto img = new ImageDto(1L);
        MockMultipartFile file = new MockMultipartFile("file", "foto.png", MediaType.IMAGE_PNG_VALUE, "test".getBytes());
        img.setContent(file.getBytes());
        Image image = new Image();

        Mockito.when(service.saveImg(img)).thenReturn(image);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/images")
                        .file(file))
                .andDo(print())
                .andExpect(status().is(200));

    }

    @Test
    @WithMockUser(username="admin",authorities={"0"})
    void download() throws Exception {
        //insert testdata
        ImageDto img = new ImageDto(1L);
        MockMultipartFile file = new MockMultipartFile("file", "foto.png", MediaType.IMAGE_PNG_VALUE, "test".getBytes());
        img.setContent(file.getBytes());

        Mockito.when(service.findById(1L)).thenReturn(img);

        //execute test
        mockMvc.perform(get("/images/1"))
                .andDo(print())
                .andExpect(status().isOk());

        Assert.assertTrue(img.getId().equals(1L));


    }
}