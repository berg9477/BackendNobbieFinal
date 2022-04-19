package Backend.NobbieFinal.service;

import Backend.NobbieFinal.dto.BabyNameDto;
import Backend.NobbieFinal.dto.UserProfileDto;
import Backend.NobbieFinal.model.UserProfile;

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
