package nl.novi.nobbie.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.novi.nobbie.NobbieFinalApplication;
import nl.novi.nobbie.dto.BabyNameDto;
import nl.novi.nobbie.model.BabyName;
import nl.novi.nobbie.model.Gender;
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
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
@ContextConfiguration(classes = {NobbieFinalApplication.class})
class BabyNameControllerTest {


    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BabyNameService service;
    @MockBean
    private BabyService babyService;
    @MockBean
    private SocialMediaAccountService socialMediaAccountService;
    @MockBean
    private UserProfileService userProfileService;
    @MockBean
    private DataSource dataSource;
    @MockBean
    private ImageService imageService;

    //insert general testdata
    BabyNameDto bnDto = new BabyNameDto(123L, "Saskia", Gender.F, 99);
    List<BabyNameDto> names = List.of(bnDto);

    @Test
    @WithMockUser(username = "admin", authorities = {"0"})
    public void returnListOfAllBabyNames() throws Exception {

        //given
        given(service.getAllNames()).willReturn(names);

        //execute test
        mockMvc.perform(get("/babyNames"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Saskia"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"0"})
    void getAllNamesFails() throws Exception {

        //Given - Exception
        Mockito.doThrow(Exception.class).when(service).getAllNames();

        //execute test
        mockMvc.perform(get("/babyNames"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Retrieving list of names failed: null"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"0"})
    public void returnNameStartingWithS() throws Exception {

        //given
        given(service.getNameStartsWith('s')).willReturn(names);

        //execute test
        mockMvc.perform(get("/nameStartsWith")
                        .param("ch", "s"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Saskia"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"0"})
    void getNameStartsWithFails() throws Exception {

        //Given - Exception
        Mockito.doThrow(Exception.class).when(service).getNameStartsWith('y');

        //execute test
        mockMvc.perform(get("/nameStartsWith")
                        .param("ch", "y"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Retrieving list of names failed: null"));

    }

    @Test
    @WithMockUser(username = "admin", authorities = {"0"})
    public void returnNamesThatContainSki() throws Exception {

        given(service.getNamesContaining("ski")).willReturn(names);

        //execute test
        mockMvc.perform(get("/namesSearch")
                        .param("input", "ski"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Saskia"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"0"})
    void getNamesContainingFails() throws Exception {

        //Given - Exception
        Mockito.doThrow(Exception.class).when(service).getNamesContaining("hallloo");

        //execute test
        mockMvc.perform(get("/namesSearch")
                        .param("input", "hallloo"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Retrieving list of names failed: null"));

    }

    @Test
    @WithMockUser(username = "admin", authorities = {"0"})
    public void InsertNewName() throws Exception {

        //create baby name for mocking insertBabyName service
        BabyName baby = new BabyName();
        baby.setId(bnDto.getId());
        baby.setName(bnDto.getName());
        baby.setListingNumber(bnDto.getListingNumber());

        //given
        Mockito.when(service.insertBabyName(bnDto)).thenReturn(baby);

        //Map input for JSON Request body
        String content = objectMapper.writeValueAsString(bnDto);

        //execute test
        mockMvc.perform(post("/babyNames")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isCreated());

    }

    @Test
    @WithMockUser(username = "admin", authorities = {"0"})
    void insertBabyNameFails() throws Exception {

        //Map input for JSON Request body
        String content = objectMapper.writeValueAsString(bnDto);

        //Name has validation @notBlank so this will trigger a validation error
        content = content.replace("Saskia", "");

        //execute test
        mockMvc.perform(post("/babyNames")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}