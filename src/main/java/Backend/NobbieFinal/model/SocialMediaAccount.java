package Backend.NobbieFinal.model;

public class SocialMediaAccount {
    private String userName;
    private MediaType socialMediaType;

    public SocialMediaAccount(String userName, MediaType socialMediaType){
        this.userName = userName;
        this.socialMediaType = socialMediaType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public MediaType getSocialMediaType() {
        return socialMediaType;
    }

    public void setSocialMediaType(MediaType socialMediaType) {
        this.socialMediaType = socialMediaType;
    }
}
