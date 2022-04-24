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
    //Retrieves a list of all registered users
    public List<UserProfileDto> getAllUsers() throws Exception {
        List<UserProfile> up = this.repos.findAll();
        if (up.size() == 0) { //Check if any users are found
            throw new Exception("No users found");
        } else {
            //Map to Dto and return list of results
            List<UserProfileDto> users = new ArrayList<>();
            up.forEach(u -> users.add(new UserProfileDto(u.getUserId(), u.getUsername(), u.getFirstname(), u.getLastname(), u.getEmailaddress(), u.getPassword(), u.getRole(), u.getEnabled())));
            return users;
        }
    }

    @Override
    //Gets user profile for a specific user
    public UserProfileDto getUser(Long id) throws Exception {
        //First check if the user id can be found
        if (this.repos.findById(id).isPresent()) {
            UserProfile up = this.repos.findById(id).get();
            //Map to Dto object and return
            return new UserProfileDto(up.getUserId(), up.getUsername(), up.getFirstname(), up.getLastname(), up.getEmailaddress(), up.getPassword(), up.getRole(), up.getEnabled());
        } else {
            throw new Exception("No user found for id: " + id);
        }
    }

    @Override
    //Registers a new user in the repository
    public UserProfile createNewUser(UserProfileDto userProfileDto) throws Exception {
        //First check if the emailaddress is not already known in the database
        if (!this.repos.existsByEmailaddress(userProfileDto.getEmailaddress())) {
            UserProfile user = new UserProfile();
            //Map Dto to UserProfile object for saving
            user.setUserId(userProfileDto.getUserId());
            user.setUsername(userProfileDto.getUsername());
            user.setFirstname(userProfileDto.getFirstname());
            user.setLastname(userProfileDto.getLastname());
            user.setEmailaddress(userProfileDto.getEmailaddress());
            user.setPassword(passwordEncoder.encode(userProfileDto.getPassword()));
            user.setRole(userProfileDto.getRole());
            user.setEnabled(userProfileDto.getEnabled());
            return this.repos.save(user);
        } else { //if the emailaddress is already in use
            throw new Exception("There is already a user using this emailaddress: " + userProfileDto.getEmailaddress());
        }
    }

    @Override
    //Saves a baby name for a specific user in the savedNamesList
    public Boolean saveBabyName(Long id, Long nameId) throws Exception {
        boolean ret = false;
        //step 1 Retrieve user profile and baby name from repository
        if (this.repos.findById(id).isPresent()) {
            UserProfile up = this.repos.findById(id).get();
            BabyName name = this.bnRepos.getById(nameId);
            //step 2 check if user didn't save this name already
            for (BabyName bn : up.getSavedNamesList()) {
                if (bn.getName().equals(name.getName())) {
                    throw new Exception("User has already saved this name: " + name.getName());
                }
            }
            //step 3 Save name
            up.addBabyNameToList(name);
            repos.save(up);
            //step 4 Check if user has connection
            Long conn = up.getConnection();
            UserProfile connection = conn != null && this.repos.findById(conn).isPresent() ? this.repos.findById(conn).get() : null;
            if (connection != null) {
                //step 5 Check if name is also on connections list
                for (BabyName bn : connection.getSavedNamesList()) {
                    if (bn.getName().equals(name.getName())) {
                        ret = true; //if both saved the same name, return true
                        break;
                    }
                }
            }
            return ret;
        } else {
            throw new Exception("No user found for id: " + id);
        }
    }

    @Override
    //Retrieves a list of baby names for a specific user filtered on yes or no with also saved by connection
    public List<BabyNameDto> getSavedNames(Long id, Boolean match) throws Exception {
        //First retrieve user profile from repository
        if (this.repos.findById(id).isPresent()) {
            UserProfile up = this.repos.findById(id).get();
            List<BabyNameDto> names = new ArrayList<>();
            if (!match) { //Only return list of names saved by this user
                for (BabyName bn : up.getSavedNamesList()) {
                    //Map to Dto object and add to result list
                    BabyNameDto babyNameDto = new BabyNameDto(bn.getId(), bn.getName(), bn.getGender(), bn.getListingNumber());
                    names.add(babyNameDto);
                }
            } else { //Only return names that have a match with connection
                Long conn = up.getConnection();
                if (conn == null) { //Check if user has a connection
                    throw new Exception("No connection found for user: " + id);
                } else if (this.repos.findById(conn).isPresent()) {
                    UserProfile connection = this.repos.findById(conn).get();
                    for (BabyName conBn : connection.getSavedNamesList()) {
                        for (BabyName bn : up.getSavedNamesList()) {
                            if (conBn == bn) { //loop through both lists and add to result list if there is a match
                                BabyNameDto babyNameDto = new BabyNameDto(bn.getId(), bn.getName(), bn.getGender(), bn.getListingNumber());
                                names.add(babyNameDto);
                            }
                        }
                    }
                } else {
                    throw new Exception("No user found for connection: " + conn);
                }
            }
            return names;
        } else {
            throw new Exception("No user found for id: " + id);
        }
    }

    @Override
    //Deletes a specific user from the repository
    public void deleteById(Long id) throws Exception {
        //Check if the user id can be found
        if (this.repos.findById(id).isPresent()) {
            this.repos.deleteById(id);
        } else {
            throw new Exception("No user found to delete");
        }
    }

    @Override
    //Resets the password of a specific user
    public UserProfileDto resetPasswordById(Long id) throws Exception {
        //Check if the user can be found
        if (this.repos.findById(id).isPresent()) {
            UserProfile up = this.repos.findById(id).get();
            up.resetPassword();
            //Map to Dto object
            UserProfileDto user = new UserProfileDto(up.getUserId(), up.getUsername(), up.getFirstname(), up.getLastname(), up.getEmailaddress(), up.getPassword(), up.getRole(), up.getEnabled());
            //For security and user privacy reasons the password needs to be encoded before saving
            up.setPassword(passwordEncoder.encode(up.getPassword()));
            this.repos.save(up);
            return user; //user is returned without the encoded password so the user sees what the new (random generated) password is
        } else {
            throw new Exception("No user found for id: " + id);
        }
    }

    @Override
    //Updates a user profile with a connection to another user
    public UserProfileDto setConnection(Long id, Long connection) throws Exception {
        //Check is the user can be found
        if (this.repos.findById(id).isPresent()) {
            UserProfile up = this.repos.findById(id).get();
            up.setConnection(connection);
            this.repos.save(up);
            //Map to Dto object and return profile as confirmation
            return new UserProfileDto(up.getUserId(), up.getUsername(), up.getFirstname(), up.getLastname(), up.getEmailaddress(), up.getPassword(), up.getRole(), up.getEnabled());
        } else {
            throw new Exception("No user found for id: " + id);
        }
    }


}
