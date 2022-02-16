package Backend.NobbieFinal.service;

import Backend.NobbieFinal.dto.UserProfileDto;
import Backend.NobbieFinal.model.UserProfile;
import Backend.NobbieFinal.repository.UserProfileRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserProfileServiceImpl implements UserProfileService{

    private final UserProfileRepository repos;

    public UserProfileServiceImpl(UserProfileRepository repos){
        this.repos = repos;
    }

    @Override
    public List<UserProfileDto> getAllUsers() {
        List<UserProfile> up =this.repos.findAll();
        List<UserProfileDto> users = new ArrayList<>();
        up.forEach(u -> users.add(new UserProfileDto(u.getUserId(), u.getFirstname(), u.getLastname(), u.getEmailaddress(), u.getPassword())));
        return users;
    }
}
