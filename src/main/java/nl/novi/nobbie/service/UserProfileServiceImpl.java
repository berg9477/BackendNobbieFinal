package nl.novi.nobbie.service;

import nl.novi.nobbie.dto.BabyNameDto;
import nl.novi.nobbie.dto.UserProfileDto;
import nl.novi.nobbie.model.BabyName;
import nl.novi.nobbie.model.UserProfile;
import nl.novi.nobbie.repository.BabyNameRepository;
import nl.novi.nobbie.repository.UserProfileRepository;
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
    private final BabyNameRepository bnRepos;

    public UserProfileServiceImpl(UserProfileRepository repos, BabyNameRepository bnRepos){
        this.repos = repos;
        this.bnRepos = bnRepos;
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
    public Boolean saveBabyName(Long id, Long nameId) {
        boolean ret = false;
        //step 1 save name
        UserProfile up = this.repos.findById(id).get();
        BabyName name = this.bnRepos.getById(nameId);
        up.addBabyNameToList(name);
        repos.save(up);
        //step 2 check if user has connection
        Long conn = up.getConnection();
        UserProfile connection = conn != null ? this.repos.findById(conn).get(): null;
        //step 3 check list
        assert connection != null;
        for(BabyName bn : connection.getSavedNamesList()){
            if(bn.getName().equals(name.getName())){
                ret = true;
            }
        }
        return ret;
    }

    @Override
    public List<BabyNameDto> getSavedNames(Long id, Boolean match) {
        UserProfile up = this.repos.findById(id).get();
        List<BabyNameDto> names = new ArrayList<>();
        if (!match) { //only return names saved by user
            for (BabyName bn : up.getSavedNamesList()) {
                BabyNameDto babyNameDto = new BabyNameDto(bn.getId(), bn.getName(), bn.getGender(), bn.getListingNumber());
                names.add(babyNameDto);
            }
        } else { //only return names that have a match with connection
            Long conn = up.getConnection();
            UserProfile connection = conn != null ? this.repos.findById(conn).get() : null;
            assert connection != null;
            for(BabyName conBn : connection.getSavedNamesList()){
                for (BabyName bn : up.getSavedNamesList()) {
                    if(conBn == bn) { //loop through both list and add if there is a match
                        BabyNameDto babyNameDto = new BabyNameDto(bn.getId(), bn.getName(), bn.getGender(), bn.getListingNumber());
                        names.add(babyNameDto);
                    }
                }
            }
        }
        return names;

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
