package Backend.NobbieFinal.service;

import Backend.NobbieFinal.dto.UserProfileDto;
import Backend.NobbieFinal.model.UserProfile;

import java.util.List;
import java.util.Map;

public interface UserProfileService {
    public List<UserProfileDto> getAllUsers();
    public UserProfile createNewUser(UserProfileDto userProfileDto);
    public UserProfile getUser(Long id);
    void updateUser(UserProfile u);
}
