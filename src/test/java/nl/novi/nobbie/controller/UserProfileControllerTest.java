package nl.novi.nobbie.controller;

import nl.novi.nobbie.NobbieFinalApplication;
import nl.novi.nobbie.dto.BabyNameDto;
import nl.novi.nobbie.dto.UserProfileDto;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.sql.DataSource;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


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
    List<UserProfileDto> users = List.of(user);
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

       Mockito.when(service.getUser(321L)).thenReturn(user);
       Mockito.when(service.saveBabyName(321L, 1L)).thenReturn(true);

       //execute test
        mockMvc.perform(post("/users/321/babyNames")
                        .param("babyNameId", "1"))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(username="admin",authorities={"0"})
    void returnListOfSavedBabyNamesForUserWithMatch() throws Exception {

        BabyNameDto name = new BabyNameDto(1L, "Saskia", Gender.F, 1);
        List<BabyNameDto> names = List.of(name);

        Mockito.when(service.getUser(321L)).thenReturn(user);
        Mockito.when(service.getSavedNames(321L, true)).thenReturn(names);

        //execute test
        mockMvc.perform(get("/users/321/babyNames")
                        .param("match", "true"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username="admin",authorities={"0"})
    void returnListOfSavedBabyNamesForUserWithoutMatch() throws Exception {

        BabyNameDto name = new BabyNameDto(1L, "Saskia", Gender.F, 1);
        List<BabyNameDto> names = List.of(name);

        Mockito.when(service.getUser(321L)).thenReturn(user);
        Mockito.when(service.getSavedNames(321L, false)).thenReturn(names);

        //execute test
        mockMvc.perform(get("/users/321/babyNames")
                        .param("match", "false"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username="admin",authorities={"0"})
    void deleteUserByIdFails() throws Exception {

        //mock triggering an exception
        Mockito.doThrow(Exception.class).when(service).deleteById(1000L);

        //execute test
        mockMvc.perform(MockMvcRequestBuilders.delete("/deleteUser")
                        .param("id", "1000"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username="admin",authorities={"0"})
    void deleteUserById() throws Exception {
        //execute test
        mockMvc.perform(MockMvcRequestBuilders.delete("/deleteUser")
                        .param("id", "1000"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username="admin",authorities={"0"})
    void resetPasswordForUser() throws Exception {

        //given
        Mockito.when(service.resetPasswordById(321L)).thenReturn(user);

        //execute test
        mockMvc.perform(MockMvcRequestBuilders.patch("/resetPassword")
                        .param("id", "321"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("password").isNotEmpty());
    }

    @Test
    @WithMockUser(username="admin",authorities={"0"})
    void resetPasswordFails() throws Exception {

        //mock triggering an exception
        Mockito.doThrow(Exception.class).when(service).resetPasswordById(321L);

        //execute test
        mockMvc.perform(MockMvcRequestBuilders.patch("/resetPassword")
                        .param("id", "321"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Resetten van het password is mislukt: null"));
    }

    @Test
    @WithMockUser(username="admin",authorities={"0"})
    void setConnectionForUser() throws Exception {
        //mock using service to connect users
        Mockito.when(service.setConnection(321L, 1L)).thenReturn(user);
        Mockito.when(service.setConnection(1L, 321L)).thenReturn(user);


        //execute test
        mockMvc.perform(MockMvcRequestBuilders.patch("/connection")
                        .param("id", "321")
                        .param("connection", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("321 en 1 zijn nu gekoppeld"));

    }

    @Test
    @WithMockUser(username="admin",authorities={"0"})
    void setConnectionFails() throws Exception {
        //mock triggering an exception
        Mockito.doThrow(Exception.class).when(service).setConnection(1000L, 321L);

        //execute test
        mockMvc.perform(MockMvcRequestBuilders.patch("/connection")
                        .param("id", "321")
                        .param("connection", "1"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


}