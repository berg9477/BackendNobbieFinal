package Backend.NobbieFinal.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SocialMediaAccount")
public class SocialMediaAccount {

    @Id
    @GeneratedValue
    Long id;
    private Long userId;
    private MediaType socialMediaType;

    public SocialMediaAccount() { }

    public SocialMediaAccount(Long userId, MediaType socialMediaType){
        this.userId = userId;
        this.socialMediaType = socialMediaType;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public MediaType getSocialMediaType() {
        return socialMediaType;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setSocialMediaType(MediaType socialMediaType) {
        this.socialMediaType = socialMediaType;
    }
}
