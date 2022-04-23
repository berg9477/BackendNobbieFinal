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
import org.mockito.Mock;
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

    @Mock
    UserProfile userProfile;

    UserProfile user = new UserProfile("username", "first", "last", "email@grs.nl", "123", 321L, Role.USER, 1);
    UserProfile user2 = new UserProfile("fret", "first", "last", "flow@grs.nl", "123", 323L, Role.USER, 1);
    UserProfile user3 = new UserProfile("gdtret", "bla", "grle", "flow2@grs.nl", "123", 3523L, Role.USER, 1);
    BabyName name = new BabyName("Saskia", Gender.F, 1);

    List<UserProfile> list = new ArrayList<UserProfile>();

    @Test
    void returnListOfUsers() throws Exception {
        list.add(user);
        list.add(user2);

        when(repos.findAll()).thenReturn(list);

        List<UserProfileDto> users = service.getAllUsers();

        assertEquals(2, list.size());
        assertEquals("email@grs.nl", list.get(0).getEmailaddress());

    }

    @Test
    void returnUserById() throws Exception {
        when(repos.findById(user.getUserId())).thenReturn(java.util.Optional.ofNullable(user));

        UserProfileDto upDto = service.getUser(user.getUserId());

        assertEquals("last", upDto.getLastname());
    }

    @Test
    void creatingNewUserFails() {
        UserProfileDto us = new UserProfileDto(1L, "username", "first", "last", "email@grs.nl", "123", Role.USER, 1);

        when(repos.existsByEmailaddress(user.getEmailaddress())).thenReturn(true);
        try {
            service.createNewUser(us);
        } catch (Exception e) {
            assertEquals("There is already a user using this emailaddress: email@grs.nl", e.getMessage());
        }
    }

    @Test
    void saveBabyNameForUserWithoutConnection() throws Exception {

        when(repos.findById(user.getUserId())).thenReturn(java.util.Optional.ofNullable(user));
        when(bnRepos.getById(1L)).thenReturn(name);

        boolean test = service.saveBabyName(user.getUserId(), 1L);

        assertFalse(test);
    }

    @Test
    void saveBabyNameForUserWithConnection() throws Exception {
        List<BabyName> b = List.of(name);
        user3.setSavedNamesList(b);
        user.setConnection(user3.getUserId());

        when(repos.findById(user.getUserId())).thenReturn(java.util.Optional.ofNullable(user));
        when(repos.findById(user3.getUserId())).thenReturn(java.util.Optional.ofNullable(user3));
        when(bnRepos.getById(1L)).thenReturn(name);

        boolean test = service.saveBabyName(user.getUserId(), 1L);

        assertTrue(test);
    }

    @Test
    void getSavedNamesForUserWithConnection() throws Exception {
        List<BabyName> b = List.of(name);
        user2.setSavedNamesList(b);
        user3.setSavedNamesList(b);
        user2.setConnection(user3.getUserId());

        when(repos.findById(user2.getUserId())).thenReturn(java.util.Optional.ofNullable(user2));
        when(repos.findById(user3.getUserId())).thenReturn(java.util.Optional.ofNullable(user3));

        List<BabyNameDto> names = service.getSavedNames(user2.getUserId(), true);

        assertEquals(1, names.size());
    }

    @Test
    void getSavedNamesForUserWithoutMatch() throws Exception {
        List<BabyName> b = List.of(name);
        user2.setSavedNamesList(b);

        when(repos.findById(user2.getUserId())).thenReturn(java.util.Optional.ofNullable(user2));

        List<BabyNameDto> names = service.getSavedNames(user2.getUserId(), false);

        assertEquals(1, names.size());
    }

    @Test
    void deleteByIdFails() {
        try {
            service.deleteById(345L);
        } catch (Exception e) {
            assertEquals("No user found to delete", e.getMessage());
        }
    }

    @Test
    void resetPasswordById() throws Exception {

        when(repos.findById(user.getUserId())).thenReturn(java.util.Optional.ofNullable(user));
        UserProfileDto newPass = service.resetPasswordById(user.getUserId());

        assertNotEquals("123", user.getPassword());
    }

    @Test
    void setConnection() throws Exception {
        when(repos.findById(user.getUserId())).thenReturn(java.util.Optional.ofNullable(user));

        UserProfileDto conn = service.setConnection(user.getUserId(), user2.getUserId());

        assertEquals(user.getUserId(), conn.getUserId());
    }
}