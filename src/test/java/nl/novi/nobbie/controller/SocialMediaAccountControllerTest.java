package nl.novi.nobbie.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.novi.nobbie.NobbieFinalApplication;
import nl.novi.nobbie.dto.SocialMediaAccountDto;
import nl.novi.nobbie.model.MediaType;
import nl.novi.nobbie.model.Role;
import nl.novi.nobbie.model.SocialMediaAccount;
import nl.novi.nobbie.model.UserProfile;
import nl.novi.nobbie.service.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.sql.DataSource;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
@ContextConfiguration(classes = {NobbieFinalApplication.class})
class SocialMediaAccountControllerTest {


    //insert general testdata
    UserProfile user = new UserProfile("username", "first", "last", "email@grs.nl", "123", 321L, Role.USER, 1);
    SocialMediaAccountDto SMADto = new SocialMediaAccountDto(123L, user, MediaType.Facebook);
    List<SocialMediaAccountDto> SMAList = List.of(SMADto);
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private SocialMediaAccountService service;
    @MockBean
    private BabyService babyService;
    @MockBean
    private BabyNameService babyNameService;
    @MockBean
    private UserProfileService userProfileService;
    @MockBean
    private DataSource dataSource;
    @MockBean
    private ImageService imageService;

    @Test
    @WithMockUser(username = "admin", authorities = {"0"})
    public void ReturnListOfAllAccounts() throws Exception {

        given(service.getAllAccounts()).willReturn(SMAList);

        //execute test
        mockMvc.perform(get("/socialMediaAccounts"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value("123"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"0"})
    void getAllAccountsFails() throws Exception {

        //Given - Exception
        Mockito.doThrow(Exception.class).when(service).getAllAccounts();

        //execute test
        mockMvc.perform(get("/socialMediaAccounts"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"0"})
    public void createNewSMAForUser() throws Exception {

        //Create SMA for mocking createSMA service
        SocialMediaAccount SMA = new SocialMediaAccount();
        SMA.setId(SMADto.getId());
        SMA.setUserId(SMADto.getUser());
        SMA.setSocialMediaType(SMADto.getSocialMediaType());

        //Given
        Mockito.when(service.createSMA(SMADto)).thenReturn(SMA);

        //Map input for JSON Request body
        String content = objectMapper.writeValueAsString(SMADto);

        //execute test
        mockMvc.perform(post("/socialMediaAccounts")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"0"})
    void createSMAFailsAtServiceLayer() throws Exception {

        //Given - Exception
        Mockito.doThrow(Exception.class).when(service).createSMA(SMADto);

        //Map input for JSON Request body
        String content = objectMapper.writeValueAsString(user);

        //execute test
        mockMvc.perform(post("/socialMediaAccounts")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"0"})
    void createSMAInvalidInputFail() throws Exception {

        //Map input for JSON Request body
        String content = objectMapper.writeValueAsString(SMADto);
        //Set value to null to trigger error
        content = content.replace("Facebook", "null");

        //execute test
        mockMvc.perform(post("/socialMediaAccounts")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @Test
    @WithMockUser(username = "admin", authorities = {"0"})
    void generateSocialMediaMessageForUser() throws Exception {

        //given
        Mockito.when(service.getSMAMessage(MediaType.Facebook, 321L)).thenReturn("Hoi Facebook, ik ben Zwanger!");

        //execute test
        mockMvc.perform(get("/socialMediaMessage")
                        .param("mediaType", "Facebook")
                        .param("id", "321"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Hoi Facebook, ik ben Zwanger!"));
    }
}