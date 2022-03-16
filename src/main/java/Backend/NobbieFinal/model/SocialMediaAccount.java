package Backend.NobbieFinal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.catalina.User;

import javax.persistence.*;

@Entity
@Table(name = "SocialMediaAccount")
public class SocialMediaAccount {

    @Id
    @GeneratedValue
    Long id;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "userId")
    UserProfile user;

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
        return this.id;
    }

    public void setId(Long id) {this.id = id;}

    @JsonIgnore
    public UserProfile getUserId() {
        return this.user;
    }

    public void setUserId(UserProfile user) {
        this.user = user;
    }

    public void setSocialMediaType(MediaType socialMediaType) {
        this.socialMediaType = socialMediaType;
    }
}
