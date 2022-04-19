package Backend.NobbieFinal.service;

import Backend.NobbieFinal.dto.UserProfileDto;
import Backend.NobbieFinal.model.UserProfile;

import java.util.List;
import java.util.Map;

public interface UserProfileService {
    List<UserProfileDto> getAllUsers();
    UserProfile createNewUser(UserProfileDto userProfileDto);
    UserProfileDto getUser(Long id);
    void updateUser(UserProfile u);
    void deleteById(Long id);
    UserProfileDto resetPasswordById(Long id);
}
