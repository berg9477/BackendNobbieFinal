package Backend.NobbieFinal.service;

import Backend.NobbieFinal.dto.UserProfileDto;
import Backend.NobbieFinal.model.BabyName;
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

    @Override
    public UserProfile createNewUser(UserProfileDto userProfileDto) {
        UserProfile user = new UserProfile();
        user.setUserId(userProfileDto.getUserId());
        user.setFirstname(userProfileDto.getFirstname());
        user.setLastname(userProfileDto.getLastname());
        user.setEmailaddress(userProfileDto.getEmailaddress());
        user.setPassword(userProfileDto.getPassword());
        return this.repos.save(user);
    }
}
