package nl.novi.nobbie.service;

import nl.novi.nobbie.dto.BabyNameDto;
import nl.novi.nobbie.dto.UserProfileDto;
import nl.novi.nobbie.model.UserProfile;

import java.util.List;

public interface UserProfileService {
    List<UserProfileDto> getAllUsers();
    UserProfile createNewUser(UserProfileDto userProfileDto);
    UserProfileDto getUser(Long id);
    void deleteById(Long id);
    UserProfileDto resetPasswordById(Long id);
    UserProfileDto setConnection(Long id, Long connection);
    Boolean saveBabyName(Long id, Long babyName);
    List<BabyNameDto> getSavedNames(Long id, Boolean match);
}
