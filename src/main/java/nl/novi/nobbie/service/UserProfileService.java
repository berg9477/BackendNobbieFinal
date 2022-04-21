package nl.novi.nobbie.service;

import nl.novi.nobbie.dto.BabyNameDto;
import nl.novi.nobbie.dto.UserProfileDto;
import nl.novi.nobbie.model.UserProfile;

import java.util.List;

public interface UserProfileService {
    List<UserProfileDto> getAllUsers() throws Exception;
    UserProfile createNewUser(UserProfileDto userProfileDto);
    UserProfileDto getUser(Long id) throws Exception;
    void deleteById(Long id) throws Exception;
    UserProfileDto resetPasswordById(Long id) throws Exception;
    UserProfileDto setConnection(Long id, Long connection) throws Exception;
    Boolean saveBabyName(Long id, Long babyName) throws Exception;
    List<BabyNameDto> getSavedNames(Long id, Boolean match) throws Exception;
}
