package Backend.NobbieFinal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "babyNames")
public class BabyName {

    @Id
    @GeneratedValue
    Long id;
    private String name;
    private Gender gender;
    private int listingNumber;

    @JsonIgnore
    @ManyToMany(mappedBy = "savedNamesList")
    private Set<UserProfile> users = new HashSet<>();

    public BabyName() { }

    public BabyName(Long id, String name, Gender gender, int listingNumber) {
        this.id = id;
        this.name = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase(); //name always needs to start with a capital
        this.gender = gender;
        this.listingNumber = listingNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getListingNumber() {
        return listingNumber;
    }

    public void setListingNumber(int listingNumber) {
        this.listingNumber = listingNumber;
    }

    public Set<UserProfile> getUsers() {
        return users;
    }

    public void setUsers(Set<UserProfile> users) {
        this.users = users;
    }
}
