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
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository repos;
    private final BabyNameRepository bnRepos;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserProfileServiceImpl(UserProfileRepository repos, BabyNameRepository bnRepos) {
        this.repos = repos;
        this.bnRepos = bnRepos;
    }

    @Override
    public List<UserProfileDto> getAllUsers() throws Exception {
        List<UserProfile> up = this.repos.findAll();
        if (up.size() == 0) {
            throw new Exception("No users found");
        } else {
            List<UserProfileDto> users = new ArrayList<>();
            up.forEach(u -> users.add(new UserProfileDto(u.getUserId(), u.getUsername(), u.getFirstname(), u.getLastname(), u.getEmailaddress(), u.getPassword(), u.getRole(), u.getEnabled())));
            return users;
        }
    }

    @Override
    public UserProfileDto getUser(Long id) throws Exception {
        if (this.repos.findById(id).isPresent()) {
            UserProfile up = this.repos.findById(id).get();
            return new UserProfileDto(up.getUserId(), up.getUsername(), up.getFirstname(), up.getLastname(), up.getEmailaddress(), up.getPassword(), up.getRole(), up.getEnabled());
        } else {
            throw new Exception("No user found for id: " + id);
        }
    }

    @Override
    public UserProfile createNewUser(UserProfileDto userProfileDto) throws Exception {
        //First check if the emailaddress is not already known in the database
        if (this.repos.existsBy(userProfileDto.getEmailaddress())) {
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
        } else { //if the emailaddress is already in use we throw an error
            throw new Exception("There is already a user using this emailaddress: " + userProfileDto.getEmailaddress());
        }
    }

    @Override
    public Boolean saveBabyName(Long id, Long nameId) throws Exception {
        boolean ret = false;
        //step 1 save name
        if (this.repos.findById(id).isPresent()) {
            UserProfile up = this.repos.findById(id).get();
            BabyName name = this.bnRepos.getById(nameId);
            if (name.getName() == null) {
                throw new Exception("No names found for id: " + nameId);
            }
            up.addBabyNameToList(name);
            repos.save(up);
            //step 2 check if user has connection
            Long conn = up.getConnection();
            UserProfile connection = this.repos.findById(conn).isPresent() ? this.repos.findById(conn).get() : null;
            //step 3 check list
            assert connection != null;
            for (BabyName bn : connection.getSavedNamesList()) {
                if (bn.getName().equals(name.getName())) {
                    ret = true;
                }
            }
            return ret;
        } else {
            throw new Exception("No user found for id: " + id);
        }
    }

    @Override
    public List<BabyNameDto> getSavedNames(Long id, Boolean match) throws Exception {
        if (this.repos.findById(id).isPresent()) {
            UserProfile up = this.repos.findById(id).get();
            List<BabyNameDto> names = new ArrayList<>();
            if (!match) { //only return names saved by user
                for (BabyName bn : up.getSavedNamesList()) {
                    BabyNameDto babyNameDto = new BabyNameDto(bn.getId(), bn.getName(), bn.getGender(), bn.getListingNumber());
                    names.add(babyNameDto);
                }
            } else { //only return names that have a match with connection
                Long conn = up.getConnection();
                if (conn == null) {
                    throw new Exception("No connection found for user: " + id);
                } else {
                    UserProfile connection = this.repos.findById(conn).get();
                    for (BabyName conBn : connection.getSavedNamesList()) {
                        for (BabyName bn : up.getSavedNamesList()) {
                            if (conBn == bn) { //loop through both list and add if there is a match
                                BabyNameDto babyNameDto = new BabyNameDto(bn.getId(), bn.getName(), bn.getGender(), bn.getListingNumber());
                                names.add(babyNameDto);
                            }
                        }
                    }
                }
            }
            return names;
        } else {
            throw new Exception("No user found for id: " + id);
        }
    }

    @Override
    public void deleteById(Long id) throws Exception {
        if (this.repos.findById(id).isPresent()) {
            this.repos.deleteById(id);
        } else {
            throw new Exception("No user found to delete");
        }
    }

    @Override
    public UserProfileDto resetPasswordById(Long id) throws Exception {
        if (this.repos.findById(id).isPresent()) {
            UserProfile up = this.repos.findById(id).get();
            up.resetPassword();
            UserProfileDto user = new UserProfileDto(up.getUserId(), up.getUsername(), up.getFirstname(), up.getLastname(), up.getEmailaddress(), up.getPassword(), up.getRole(), up.getEnabled());
            up.setPassword(passwordEncoder.encode(up.getPassword()));
            this.repos.save(up);
            return user;
        } else {
            throw new Exception("No user found for id: " + id);
        }
    }

    @Override
    public UserProfileDto setConnection(Long id, Long connection) throws Exception {
        if (this.repos.findById(id).isPresent()) {
            UserProfile up = this.repos.findById(id).get();
            up.setConnection(connection);
            this.repos.save(up);
            return new UserProfileDto(up.getUserId(), up.getUsername(), up.getFirstname(), up.getLastname(), up.getEmailaddress(), up.getPassword(), up.getRole(), up.getEnabled());
        } else {
            throw new Exception("No user found for id: " + id);
        }
    }


}
