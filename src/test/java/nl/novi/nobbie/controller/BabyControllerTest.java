package nl.novi.nobbie.controller;

import nl.novi.nobbie.NobbieFinalApplication;
import nl.novi.nobbie.dto.BabyDto;
import nl.novi.nobbie.model.Baby;
import nl.novi.nobbie.model.Gender;
import nl.novi.nobbie.model.Role;
import nl.novi.nobbie.model.UserProfile;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.novi.nobbie.service.*;
import org.junit.jupiter.api.Test;

import static org.mockito.BDDMockito.given;

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
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes={NobbieFinalApplication.class})
class BabyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BabyService service;
    @MockBean
    private BabyNameService babyNameService;
    @MockBean
    private SocialMediaAccountService socialMediaAccountService;
    @MockBean
    private UserProfileService userProfileService;
    @MockBean
    private DataSource dataSource;
    @MockBean
    private ImageService imageService;

    //insert testdata
    UserProfile user = new UserProfile("username", "first", "last", "email@grs.nl", "123", 321L, Role.USER, 1);
    BabyDto baby = new BabyDto(123L, "baba", Gender.M, LocalDate.of(2022, 1, 22), false, user);
    List<BabyDto> babies = Arrays.asList(baby);


    @Test
    @WithMockUser(username="admin",authorities={"0"})
    public void returnListOfBabys() throws Exception {

        given(service.getAllBabies()).willReturn(babies);

        //execute test
        mockMvc.perform(get("/babies"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nickname").value("baba"));
    }

    @Test
    @WithMockUser(username="user",authorities={"1"})
    public void notAuthorisedForBabiesList() throws Exception {

        given(service.getAllBabies()).willReturn(babies);

        //execute test
        mockMvc.perform(get("/babies"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username="admin",authorities={"0"})
    public void returnListOfBabysByUserId() throws Exception{

        Mockito.when(service.getBabiesById(123L)).thenReturn(babies);


        //execute test
        mockMvc.perform(get("/babiesForUser")
                .param("id", "123"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].birthdate").value("2022-01-22"))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    @Test
    @WithMockUser(username="admin",authorities={"0"})
    public void createNewBaby() throws Exception {

        Baby b = new Baby();
        b.setId(baby.getId());
        b.setNickname(baby.getNickname());
        b.setBirthdate(baby.getBirthdate());
        b.setGender(baby.getGender());
        b.setExpected(baby.getExpected(), baby.getBirthdate());
        b.setWeeksLeft(b.getWeeksLeft(baby.getBirthdate()));
        b.setUser(baby.getUser());

        Mockito.when(service.createBaby(baby)).thenReturn(b);

        //set up request body
        String content = objectMapper.writeValueAsString(baby);
        content = content.replace("}", ",\"userProfile\":");
        content += objectMapper.writeValueAsString(user) + "}";


        //execute test
        mockMvc.perform(post("/babies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andDo(print())
                .andExpect(status().isCreated());
    }

}