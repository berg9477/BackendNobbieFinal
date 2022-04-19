package Backend.NobbieFinal.service;

import Backend.NobbieFinal.dto.UserProfileDto;
import Backend.NobbieFinal.model.UserProfile;
import Backend.NobbieFinal.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserProfileServiceImpl implements UserProfileService{

    @Autowired
    private PasswordEncoder passwordEncoder;

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
    public UserProfileDto getUser(Long id) {
        UserProfile up = this.repos.findById(id).get();
        return new UserProfileDto(up.getUserId(), up.getUsername(), up.getFirstname(), up.getLastname(), up.getEmailaddress(), up.getPassword(), up.getRole(), up.getEnabled());
    }

    @Override
    public UserProfile createNewUser(UserProfileDto userProfileDto) {
        UserProfile user = new UserProfile();
        user.setUserId(userProfileDto.getUserId());
        user.setUsername(userProfileDto.getUsername());
        user.setFirstname(userProfileDto.getFirstname());
        user.setLastname(userProfileDto.getLastname());
        user.setEmailaddress(userProfileDto.getEmailaddress());
        user.setPassword(passwordEncoder.encode(userProfileDto.getPassword()));
        user.setRole(userProfileDto.getRole());
        user.setEnabled(userProfileDto.getEnabled());
        return this.repos.save(user);
    }
    @Override
    public void updateUser(UserProfile u) {
        repos.save(u);
    }

    @Override
    public void deleteById(Long id) {
        this.repos.deleteById(id);
    }

    @Override
    public UserProfileDto resetPasswordById(Long id) {
        UserProfile up = this.repos.findById(id).get();
        up.resetPassword();
        UserProfileDto user = new UserProfileDto(up.getUserId(), up.getUsername(), up.getFirstname(), up.getLastname(), up.getEmailaddress(), up.getPassword(), up.getRole(), up.getEnabled());
        up.setPassword(passwordEncoder.encode(up.getPassword()));
        this.repos.save(up);
        return user;
    }

    @Override
    public UserProfileDto setConnection(Long id, Long connection) {
        try{UserProfile up = this.repos.findById(id).get();
            up.setConnection(connection);
            this.repos.save(up);
            return new UserProfileDto(up.getUserId(), up.getUsername(), up.getFirstname(), up.getLastname(), up.getEmailaddress(), up.getPassword(), up.getRole(), up.getEnabled());
        } catch (Exception e)
        {
            return new UserProfileDto(null, null, null, null, null, null, null, 0);
        }

    }


}
