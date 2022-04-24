package nl.novi.nobbie.service;

import nl.novi.nobbie.NobbieFinalApplication;
import nl.novi.nobbie.dto.BabyDto;
import nl.novi.nobbie.model.Baby;
import nl.novi.nobbie.model.Gender;
import nl.novi.nobbie.model.Role;
import nl.novi.nobbie.model.UserProfile;
import nl.novi.nobbie.repository.BabyRepository;
import nl.novi.nobbie.repository.UserProfileRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@SpringBootTest
@ContextConfiguration(classes = {NobbieFinalApplication.class})
class BabyServiceImplTest {

    //Insert general testdata
    UserProfile user = new UserProfile("username", "first", "last", "email@grs.nl", "123", 321L, Role.USER, 1);
    Baby b = new Baby("baba", Gender.X, LocalDate.of(2022, 8, 22), true, user);
    Baby b2 = new Baby("Peter", Gender.M, LocalDate.of(2020, 1, 22), false, user);
    Baby b3 = new Baby("Kristel", Gender.F, LocalDate.of(2018, 1, 22), false, user);
    List<Baby> list = new ArrayList<>();
    @Autowired
    private BabyService service;
    @MockBean
    private BabyRepository repos;
    @MockBean
    private UserProfileRepository upRepos;

    @Test
    void getAllBabies() throws Exception {
        //Add to list for mocking findAll action
        list.add(b);
        list.add(b2);
        list.add(b3);

        given(repos.findAll()).willReturn(list);

        //Execute test
        List<BabyDto> kids = service.getAllBabies();

        //Check results
        assertEquals(3, kids.size());
    }

    @Test
    void getBabiesByIdFails() {
        try {
            //Execute test
            service.getBabiesById(1L);
        } catch (Exception e) {
            //Check results
            assertEquals("No user found for userId: 1", e.getMessage());
        }
    }

    @Test
    void returnListOfBabiesForUser() throws Exception {
        //Add baby to list for mocking findByUser action
        list.add(b);

        //Given
        when(upRepos.findById(user.getUserId())).thenReturn(java.util.Optional.ofNullable(user));
        when(repos.findByUser(user)).thenReturn(list);

        //Execute test
        List<BabyDto> kids = service.getBabiesById(user.getUserId());

        //Check results
        assertEquals(1, kids.size());
        assertEquals("baba", kids.get(0).getNickname());
    }

}