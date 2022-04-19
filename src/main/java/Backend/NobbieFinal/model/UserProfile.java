package Backend.NobbieFinal.model;


import javax.persistence.*;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "users")
public class UserProfile {
    @Id
    @GeneratedValue
    Long userId;

    private String username;
    private String firstname;
    private String lastname;
    private String emailaddress;
    private String password;

    @OneToMany(mappedBy = "user")
    List<Baby> babies;

    @OneToMany(mappedBy = "user")
    List<SocialMediaAccount> socialMediaAccounts;

    @ManyToMany
    List<BabyName> savedNamesList = new ArrayList<>();

    private Role role;

    private int enabled;

    private Long connection;

    public UserProfile(){ }

    public UserProfile(String username, String first, String last, String email, String password, Long userId, Role role, int enabled){
        this.username = username;
        this.firstname = first;
        this.lastname = last;
        this.emailaddress = email;
        this.password = password;
        this.userId = userId;
        this.role = role;
        this.enabled = enabled;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmailaddress() {
        return emailaddress;
    }

    public void setEmailaddress(String emailaddress) {
        this.emailaddress = emailaddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void resetPassword() {
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 7; i++)
        {  // each iteration of the loop randomly chooses a character from chars
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }
        this.password = sb.toString();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Baby> getBabies() {
        return babies;
    }

    public void setBabies(List<Baby> babies) {
        this.babies = babies;
    }

    public List<SocialMediaAccount> getSocialMediaAccounts() {
        return socialMediaAccounts;
    }

    public void setSocialMediaAccounts(List<SocialMediaAccount> socialMediaAccounts) {
        this.socialMediaAccounts = socialMediaAccounts;
    }

    public List<BabyName> getSavedNamesList() {
        return savedNamesList;
    }

    public void setSavedNamesList(List<BabyName> savedNamesList) {
        this.savedNamesList = savedNamesList;
    }
    public void addBabyNameToList(BabyName name) {
        savedNamesList.add(name);
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public Long getConnection() {
        return connection;
    }

    public void setConnection(Long connection) {
        //we only perform this action if the userId is not the same as current userId
        if(!connection.equals(this.getUserId())) {
            this.connection = connection;
        }
    }
}
