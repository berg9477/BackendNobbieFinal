package nl.novi.nobbie.controller;

import nl.novi.nobbie.NobbieFinalApplication;
import nl.novi.nobbie.dto.ImageDto;
import nl.novi.nobbie.model.Image;
import nl.novi.nobbie.model.Role;
import nl.novi.nobbie.model.UserProfile;
import nl.novi.nobbie.service.*;
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
import java.io.IOException;


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


    //insert testdata
    MockMultipartFile file = new MockMultipartFile("file", "foto.png", MediaType.IMAGE_PNG_VALUE, "test".getBytes());
    UserProfile user = new UserProfile("username", "first", "last", "email@grs.nl", "123", 321L, Role.USER, 1);
    ImageDto img = new ImageDto(321L, user);


    @Test
    @WithMockUser(username="admin",authorities={"0"})
    public void upload() throws Exception {

        img.content = file.getBytes();

        Mockito.when(service.saveImg(321L, file)).thenReturn("succes");

        mockMvc.perform(MockMvcRequestBuilders.multipart("/images/321")
                        .file(file))
                .andDo(print())
                .andExpect(status().is(201));

    }

    @Test
    @WithMockUser(username="admin",authorities={"0"})
    void download() throws Exception {
        //insert testdata
        img.content = file.getBytes();

        Mockito.when(service.findById(321L)).thenReturn(img);

        //execute test
        mockMvc.perform(get("/images/321"))
                .andDo(print())
                .andExpect(status().isOk());

        Assert.assertTrue(img.getId().equals(321L));


    }

    @Test
    @WithMockUser(username="admin",authorities={"0"})
    void testUploadFails() throws Exception {
        img.content = file.getBytes();

        Mockito.doThrow(Exception.class).when(service).saveImg(321L, file);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/images/321")
                        .file(file))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username="admin",authorities={"0"})
    void testDownloadFails() throws Exception {
        //insert testdata
        img.content = file.getBytes();

        Mockito.doThrow(Exception.class).when(service).findById(321L);

        //execute test
        mockMvc.perform(get("/images/321"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}