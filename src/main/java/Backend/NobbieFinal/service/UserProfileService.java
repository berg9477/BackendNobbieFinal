package Backend.NobbieFinal.service;

import Backend.NobbieFinal.dto.UserProfileDto;

import java.util.List;

public interface UserProfileService {
    public List<UserProfileDto> getAllUsers();
}
