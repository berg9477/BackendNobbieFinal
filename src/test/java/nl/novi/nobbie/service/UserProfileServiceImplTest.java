package nl.novi.nobbie.service;

import nl.novi.nobbie.NobbieFinalApplication;
import nl.novi.nobbie.dto.BabyNameDto;
import nl.novi.nobbie.dto.UserProfileDto;
import nl.novi.nobbie.model.BabyName;
import nl.novi.nobbie.model.Gender;
import nl.novi.nobbie.model.Role;
import nl.novi.nobbie.model.UserProfile;
import nl.novi.nobbie.repository.BabyNameRepository;
import nl.novi.nobbie.repository.UserProfileRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ContextConfiguration(classes = {NobbieFinalApplication.class})
class UserProfileServiceImplTest {

    @Autowired
    UserProfileService service;

    @MockBean
    UserProfileRepository repos;
    @MockBean
    BabyNameRepository bnRepos;

    //Setting general input data
    UserProfile user = new UserProfile("username", "first", "last", "email@grs.nl", "123", 321L, Role.USER, 1);
    UserProfile user2 = new UserProfile("fret", "first", "last", "flow@grs.nl", "123", 323L, Role.USER, 1);
    UserProfile user3 = new UserProfile("Blub", "bla", "Hallo", "flow2@grs.nl", "123", 3523L, Role.USER, 1);
    BabyName name = new BabyName("Saskia", Gender.F, 1);
    List<UserProfile> list = new ArrayList<>();

    @Test
    void returnListOfUsers() throws Exception {
        //Add to list for mocking findAll action
        list.add(user);
        list.add(user2);

        //given
        when(repos.findAll()).thenReturn(list);

        //Execute test
        List<UserProfileDto> users = service.getAllUsers();

        //Check results
        assertEquals(2, users.size());
        assertEquals("email@grs.nl", users.get(0).getEmailaddress());

    }

    @Test
    void returnUserById() throws Exception {
        //Given
        when(repos.findById(user.getUserId())).thenReturn(java.util.Optional.ofNullable(user));

        //Execute test
        UserProfileDto upDto = service.getUser(user.getUserId());

        //Check results
        assertEquals("last", upDto.getLastname());
    }

    @Test
    void creatingNewUserFails() {
        //Create UserProfileDTO object for input data of service
        UserProfileDto us = new UserProfileDto(1L, "username", "first", "last", "email@grs.nl", "123", Role.USER, 1);

        //given
        when(repos.existsByEmailaddress(user.getEmailaddress())).thenReturn(true);
        try {
            //Execute test
            service.createNewUser(us);
        } catch (Exception e) {
            //Check results
            assertEquals("There is already a user using this emailaddress: email@grs.nl", e.getMessage());
        }
    }

    @Test
    void saveBabyNameForUserWithoutConnection() throws Exception {
        //given
        when(repos.findById(user.getUserId())).thenReturn(java.util.Optional.ofNullable(user));
        when(bnRepos.getById(1L)).thenReturn(name);

        //Execute test
        boolean test = service.saveBabyName(user.getUserId(), 1L);

        //Check results
        assertFalse(test);
    }

    @Test
    void saveBabyNameForUserWithConnectionMatch() throws Exception {
        //give user3 a list of saved baby names containing the name that being saved in the test,
        // // and set connection between the two
        List<BabyName> b = List.of(name);
        user3.setSavedNamesList(b);
        user.setConnection(user3.getUserId());

        //given
        when(repos.findById(user.getUserId())).thenReturn(java.util.Optional.ofNullable(user));
        when(repos.findById(user3.getUserId())).thenReturn(java.util.Optional.ofNullable(user3));
        when(bnRepos.getById(1L)).thenReturn(name);

        //Execute test
        boolean test = service.saveBabyName(user.getUserId(), 1L);

        //Check results
        assertTrue(test);
    }

    @Test
    void getSavedNamesForUserWithConnection() throws Exception {
        //create a list of saved names for user2 and user3
        //and set connection for user2 to user3
        List<BabyName> b = List.of(name);
        user2.setSavedNamesList(b);
        user3.setSavedNamesList(b);
        user2.setConnection(user3.getUserId());

        //Given
        when(repos.findById(user2.getUserId())).thenReturn(java.util.Optional.ofNullable(user2));
        when(repos.findById(user3.getUserId())).thenReturn(java.util.Optional.ofNullable(user3));

        //Execute test
        List<BabyNameDto> names = service.getSavedNames(user2.getUserId(), true);

        //Check results
        assertEquals(1, names.size());
    }

    @Test
    void getSavedNamesForUserWithoutMatch() throws Exception {
        //Create a list of saved baby names for user2
        List<BabyName> b = List.of(name);
        user2.setSavedNamesList(b);

        //Given
        when(repos.findById(user2.getUserId())).thenReturn(java.util.Optional.ofNullable(user2));

        //Execute test
        List<BabyNameDto> names = service.getSavedNames(user2.getUserId(), false);

        //Check results
        assertEquals(1, names.size());
    }

    @Test
    void deleteByIdFails() {
        //setting a userID that's so long that it won't exist in repository
        Long userId = 3457788454L;
        try {
            //Execute test
            service.deleteById(userId);
        } catch (Exception e) {
            //Check results
            assertEquals("No user found to delete", e.getMessage());
        }
    }

    @Test
    void resetPasswordById() throws Exception {
        //Given
        when(repos.findById(user.getUserId())).thenReturn(java.util.Optional.ofNullable(user));

        //Execute test
        service.resetPasswordById(user.getUserId());

        //Check results
        assertNotEquals("123", user.getPassword());
    }

    @Test
    void setConnection() throws Exception {
        //Given
        when(repos.findById(user.getUserId())).thenReturn(java.util.Optional.ofNullable(user));

        //Execute test
        UserProfileDto conn = service.setConnection(user.getUserId(), user2.getUserId());

        //Check results
        assertEquals(user.getUserId(), conn.getUserId());
    }
}