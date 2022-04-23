package nl.novi.nobbie.service;

import nl.novi.nobbie.NobbieFinalApplication;
import nl.novi.nobbie.dto.SocialMediaAccountDto;
import nl.novi.nobbie.model.*;
import nl.novi.nobbie.repository.SocialMediaAccountRepository;
import nl.novi.nobbie.repository.UserProfileRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@ContextConfiguration(classes={NobbieFinalApplication.class})
class SocialMediaAccountServiceImplTest {

    @Autowired
    SocialMediaAccountService service;

    @MockBean
    UserProfileRepository upRepos;
    @MockBean
    SocialMediaAccountRepository repos;

    @Mock
    SocialMediaAccount sma;

    UserProfile user = new UserProfile("username", "first", "last", "email@grs.nl", "123", 321L, Role.USER, 1);
    SocialMediaAccount facebook = new SocialMediaAccount(user, MediaType.Facebook);
    SocialMediaAccount insta = new SocialMediaAccount(user, MediaType.Instagram);
    SocialMediaAccount tweet = new SocialMediaAccount(user, MediaType.Twitter);
    List<SocialMediaAccount> list = new ArrayList<SocialMediaAccount>();

    @Test
    void returnListOfAllAccounts() throws Exception {
        list.add(facebook);
        list.add(insta);
        list.add(tweet);

        when(repos.findAll()).thenReturn(list);

        List<SocialMediaAccountDto> acc = service.getAllAccounts();

        assertEquals(3, acc.size());
        assertEquals(MediaType.Instagram, acc.get(1).getSocialMediaType());
    }

    @Test
    void creatingSMAFails() {
        SocialMediaAccountDto smaDto = new SocialMediaAccountDto(null, user, MediaType.Facebook);

        try{
            service.createSMA(smaDto);
        } catch (Exception e) {
            assertEquals("Please provide SocialMediaAccountDto object", e.getMessage());
        }
    }

    @Test
    void generateSMAMessage() throws Exception {
        //in order to make this test work the user needs to have at least one baby registered.
        Baby b = new Baby("baba", Gender.X, LocalDate.of(2022, 8, 22), true, user);
        List<Baby> babies = List.of(b);
        user.setBabies(babies);
        when(upRepos.findById(user.getUserId())).thenReturn(java.util.Optional.ofNullable(user));
        when(upRepos.getById(user.getUserId())).thenReturn(user);

        String msg = service.getSMAMessage(facebook.getSocialMediaType(), user.getUserId());
        assertTrue(msg.contains("Facebook"));
        }
}