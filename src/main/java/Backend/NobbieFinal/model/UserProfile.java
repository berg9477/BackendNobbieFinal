package Backend.NobbieFinal.model;

import java.util.ArrayList;
import java.util.List;

public class UserProfile {
    private String firstname;
    private String lastname;
    private String emailaddress;
    private String password;
    private String userId;
    private List<SocialMediaAccount> socialMediaAccounts = new ArrayList<SocialMediaAccount>();
    private List<BabyName> savedNamesList = new ArrayList<BabyName>();
    private List<Baby> kids = new ArrayList<Baby>();

    public UserProfile(String first, String last, String email, String password, String userId){
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    //methods for socialMediaAccounts list
    public List<SocialMediaAccount> getSocialMediaAccounts() {
        return socialMediaAccounts;
    }

    public void setSocialMediaAccounts(List<SocialMediaAccount> socialMediaAccounts) {
        this.socialMediaAccounts = socialMediaAccounts;
    }

    public void addSocialMediaAccount(SocialMediaAccount sma){
        socialMediaAccounts.add(sma);
    }

    //methods for savedNamesList
    public List<BabyName> getSavedNamesList() {
        return savedNamesList;
    }

    public void setSavedNamesList(List<BabyName> savedNamesList) {
        this.savedNamesList = savedNamesList;
    }

    public void addName(BabyName name){
        savedNamesList.add(name);
    }

    //methods for kids list
    public List<Baby> getKids() {
        return kids;
    }

    public void setKids(List<Baby> kids) {
        this.kids = kids;
    }

    public void addKid(Baby baby){
        kids.add(baby);
    }

}
