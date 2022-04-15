package Backend.NobbieFinal.controller;

import Backend.NobbieFinal.NobbieFinalApplication;
import Backend.NobbieFinal.dto.SocialMediaAccountDto;
import Backend.NobbieFinal.model.MediaType;
import Backend.NobbieFinal.model.Role;
import Backend.NobbieFinal.model.SocialMediaAccount;
import Backend.NobbieFinal.model.UserProfile;
import Backend.NobbieFinal.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
@ContextConfiguration(classes={NobbieFinalApplication.class})
class SocialMediaAccountControllerTest {

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

    //insert testdata
    UserProfile user = new UserProfile("username", "first", "last", "email@grs.nl", "123", 321L, Role.USER, 1);
    SocialMediaAccountDto SMADto = new SocialMediaAccountDto(123L, user, MediaType.Facebook);
    List<SocialMediaAccountDto> SMAList = Arrays.asList(SMADto);

    @Test
    @WithMockUser(username="admin",authorities={"0"})
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
    @WithMockUser(username="admin",authorities={"0"})
    public void createNewSMAForUser() throws Exception {

        SocialMediaAccount SMA = new SocialMediaAccount();
        SMA.setId(SMADto.getId());
        SMA.setUserId(SMADto.getUser());
        SMA.setSocialMediaType(SMADto.getSocialMediaType());

        Mockito.when(service.createSMA(SMADto)).thenReturn(SMA);

        String content = objectMapper.writeValueAsString(SMADto);

        //execute test
        mockMvc.perform(post("/socialMediaAccounts")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isCreated());


    }
}