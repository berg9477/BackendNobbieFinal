package Backend.NobbieFinal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "users")
public class UserProfile {
    @Id
    @GeneratedValue
    Long userId;

    private String firstname;
    private String lastname;
    private String emailaddress;
    private String password;

    @OneToMany(mappedBy = "user")
    List<Baby> babies;

    @OneToMany(mappedBy = "user")
    List<SocialMediaAccount> socialMediaAccounts;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "User_babynames",
            joinColumns = { @JoinColumn(name = "userId") },
            inverseJoinColumns = { @JoinColumn(name = "babyName") }
    )
    Set<BabyName> savedNamesList = new HashSet<>();

    public UserProfile(){ }

    public UserProfile(String first, String last, String email, String password, Long userId){
        this.firstname = first;
        this.lastname = last;
        this.emailaddress = email;
        this.password = password;
        this.userId = userId;
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

    public Set<BabyName> getSavedNamesList() {
        return savedNamesList;
    }

    public void setSavedNamesList(Set<BabyName> savedNamesList) {
        this.savedNamesList = savedNamesList;
    }

}
