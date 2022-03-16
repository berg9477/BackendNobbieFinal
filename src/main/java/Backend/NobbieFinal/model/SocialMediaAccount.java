package Backend.NobbieFinal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "SocialMediaAccount")
public class SocialMediaAccount {

    @Id
    @GeneratedValue
    Long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserProfile user;

    private MediaType socialMediaType;

    public SocialMediaAccount() { }

    public SocialMediaAccount(UserProfile user, MediaType socialMediaType){
        this.user = user;
        this.socialMediaType = socialMediaType;
    }

    public MediaType getSocialMediaType() {
        return socialMediaType;
    }

    public Long getId() {
        return id;
    }

    @JsonIgnore
    public UserProfile getUser() {
        return user;
    }

    public void setUser(UserProfile user) {
        this.user = user;
    }

    public void setSocialMediaType(MediaType socialMediaType) {
        this.socialMediaType = socialMediaType;
    }
}
