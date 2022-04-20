package nl.novi.nobbie.controller;

import nl.novi.nobbie.NobbieFinalApplication;
import nl.novi.nobbie.dto.UserProfileDto;
import nl.novi.nobbie.model.BabyName;
import nl.novi.nobbie.model.Gender;
import nl.novi.nobbie.model.Role;
import nl.novi.nobbie.model.UserProfile;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.novi.nobbie.service.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
@ContextConfiguration(classes={NobbieFinalApplication.class})
class UserProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserProfileService service;
    @MockBean
    private SocialMediaAccountService socialMediaAccountService;
    @MockBean
    private BabyService babyService;
    @MockBean
    private BabyNameService babyNameService;
    @MockBean
    private DataSource dataSource;
    @MockBean
    private ImageService imageService;

    //insert testdata
    UserProfileDto user = new UserProfileDto(321L, "test01", "first", "last", "email@grs.nl", "1234567", Role.USER, 1);
    List<UserProfileDto> users = Arrays.asList(user);
    UserProfile u = new UserProfile(user.getUsername(), user.getFirstname(), user.getLastname(), user.getEmailaddress(), user.getPassword(), user.getUserId(), user.getRole(), user.getEnabled());

    @Test
    @WithMockUser(username="admin",authorities={"0"})
    public void returnListOfAllUsers() throws Exception {

        given(service.getAllUsers()).willReturn(users);

        //execute test
        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].emailaddress").value("email@grs.nl"));

    }

    @Test
    @WithMockUser(username="admin",authorities={"0"})
    public void returnUserForId() throws Exception {

        Mockito.when(service.getUser(123L)).thenReturn(user);

        //execute test
        mockMvc.perform(get("/user")
                        .param("id", "123"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("username").value("test01"));
    }

    @Test
    @WithMockUser(username="admin",authorities={"0"})
    public void passwordNotLongEnough() throws Exception {

        //new user is set so the password can be set at an invalid value
        UserProfileDto user2 = new UserProfileDto(321L, "test01", "first", "last", "email@grs.nl", "123", Role.USER, 1);

        Mockito.when(service.createNewUser(user)).thenReturn(u);
        String content = objectMapper.writeValueAsString(user2);
        System.out.println(content);

        //execute test
        mockMvc.perform(post("/createUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username="admin",authorities={"0"})
    public void userSuccessfullyCreated() throws Exception {

        Mockito.when(service.createNewUser(user)).thenReturn(u);
        String content = objectMapper.writeValueAsString(user);
        System.out.println(content);

        //execute test
        mockMvc.perform(post("/createUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username="admin",authorities={"0"})
    public void saveBabyNameForUser() throws Exception {

        BabyName bn = new BabyName("Saskia", Gender.F, 99);
        List<BabyName> names = Arrays.asList(bn);

        Mockito.when(service.getUser(321L)).thenReturn(user);
        Mockito.when(babyNameService.findNameById(123L)).thenReturn(bn);


        String content = objectMapper.writeValueAsString(names);
        System.out.println(content);

        //execute test
        mockMvc.perform(post("/users/321/babynames")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isOk());
    }
}