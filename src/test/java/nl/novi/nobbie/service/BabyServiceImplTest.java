package nl.novi.nobbie.service;

import nl.novi.nobbie.NobbieFinalApplication;
import nl.novi.nobbie.dto.BabyDto;
import nl.novi.nobbie.dto.BabyNameDto;
import nl.novi.nobbie.model.*;
import nl.novi.nobbie.repository.BabyRepository;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@SpringBootTest
@ContextConfiguration(classes={NobbieFinalApplication.class})
class BabyServiceImplTest {

    @Autowired
    private BabyService service;

    @MockBean
    private BabyRepository repos;
    @MockBean
    private UserProfileRepository upRepos;

    @Mock
    Baby baby;
    @Mock
    UserProfile userProfile;

    UserProfile user = new UserProfile("username", "first", "last", "email@grs.nl", "123", 321L, Role.USER, 1);
    Baby b = new Baby("baba", Gender.X, LocalDate.of(2022, 8, 22), true, user);
    Baby b2 = new Baby("Peter", Gender.M, LocalDate.of(2020, 1, 22), false, user);
    Baby b3 = new Baby("Kristel", Gender.F, LocalDate.of(2018, 1, 22), false, user);


    List<Baby> list = new ArrayList<Baby>();

    @Test
    void getAllBabies() throws Exception {
        list.add(b);
        list.add(b2);
        list.add(b3);

        given(repos.findAll()).willReturn(list);

        //test
        List<BabyDto> kids = service.getAllBabies();

        assertEquals(3, kids.size());
    }

    @Test
    void getBabiesByIdFails() throws Exception {
        //test
       try {
           List<BabyDto> kids = service.getBabiesById(1L);
       } catch (Exception e) {
           assertEquals("No user found for userId: 1", e.getMessage());
       }
    }
    @Test
    void returnListOfBabiesForUser() throws Exception {
        list.add(b);

        when(upRepos.findById(user.getUserId())).thenReturn(java.util.Optional.ofNullable(user));
        when(repos.findByUser(user)).thenReturn(list);

        //test
        List<BabyDto> kids = service.getBabiesById(user.getUserId());

        assertEquals(1, kids.size());
        assertEquals("baba",kids.get(0).getNickname());
    }

}