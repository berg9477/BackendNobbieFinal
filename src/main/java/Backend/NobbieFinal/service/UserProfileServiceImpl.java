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
        up.forEach(u -> users.add(new UserProfileDto(u.getUserId(), u.getUsername(), u.getFirstname(), u.getLastname(), u.getEmailaddress(), u.getPassword(), u.getRole(), u.getEnabled())));
        return users;
    }

    @Override
    public UserProfile getUser(Long id) {
        return this.repos.findById(id).get();
    }

    @Override
    public UserProfile createNewUser(UserProfileDto userProfileDto) {
        UserProfile user = new UserProfile();
        user.setUserId(userProfileDto.getUserId());
        user.setUsername(userProfileDto.getUsername());
        user.setFirstname(userProfileDto.getFirstname());
        user.setLastname(userProfileDto.getLastname());
        user.setEmailaddress(userProfileDto.getEmailaddress());
        user.setPassword(userProfileDto.getPassword());
        user.setRole(userProfileDto.getRole());
        user.setEnabled(userProfileDto.getEnabled());
        return this.repos.save(user);
    }
    @Override
    public void updateUser(UserProfile u) {
        repos.save(u);
    }
}
