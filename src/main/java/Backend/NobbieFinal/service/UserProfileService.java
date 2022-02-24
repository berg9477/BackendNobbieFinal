package Backend.NobbieFinal.service;

import Backend.NobbieFinal.dto.UserProfileDto;
import Backend.NobbieFinal.model.UserProfile;

import java.util.List;

public interface UserProfileService {
    public List<UserProfileDto> getAllUsers();
    public UserProfile createNewUser(UserProfileDto userProfileDto);
}
