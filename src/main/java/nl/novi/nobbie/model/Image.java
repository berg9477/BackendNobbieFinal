package nl.novi.nobbie.model;

import javax.persistence.*;

@Entity
public class Image {
    @Id
    @GeneratedValue
    Long id;

    @Lob
    public byte[] content;

    @OneToOne
    @JoinColumn(name = "userId")
    public UserProfile user;

    public Image(){}

    public Image(byte[] content, UserProfile user){
        this.content = content;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public UserProfile getUser() {
        return user;
    }

    public void setUser(UserProfile user) {
        this.user = user;
    }
}