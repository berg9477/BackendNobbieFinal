package nl.novi.nobbie.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "babies")
public class Baby {

    @Id
    @GeneratedValue
    Long id;

    private String nickname;

    private Gender gender;

    private LocalDate birthdate;

    private Boolean expected;

    private int weeksLeft;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "userId")
    @JsonIgnore
    UserProfile user;

    public Baby (){}

    public Baby (String name, Gender gen, LocalDate date, Boolean expected, UserProfile userProfile){
        this.nickname = name;
        this.gender = gen;
        this.expected = expected;
        this.birthdate = date;
        LocalDate today = LocalDate.now();
        //someone cannot be overdue more than 1 month
        if(expected && today.isAfter(date.plusMonths(1))){
            this.expected = false;
            this.weeksLeft = 0;
            System.out.println("expected set false because person cant be overdue more then 2 months");
        } else if(expected){
            //calculate the number of weeks left till date of birth
            Number weeks = Math.abs(ChronoUnit.WEEKS.between(date, today));
            this.weeksLeft = weeks.intValue();
        } else { //if expected is false weeksLeft is always 0
            this.weeksLeft = 0;
        }
        this.user = userProfile;
    }

    public Long getId() {return id; }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public Boolean getExpected() {
        return expected;
    }

    public void setExpected(Boolean expected, LocalDate date) {
        //if expected goes from true to false, the birthdate and weeksLeft will be updated.
        if(!expected){
            setBirthdate(date);
            this.weeksLeft = 0;
        }
        this.expected = expected;
    }

    public void setWeeksLeft(int weeksLeft) { this.weeksLeft = weeksLeft; }

    public int getWeeksLeft(LocalDate date) {
        LocalDate today = LocalDate.now();
        if(today.isAfter(date.plusMonths(1))) {
            System.out.println("expected set false because person cant be overdue more then 2 months");
            this.expected = false;
            return 0;
        }
        else{
            Number weeks = Math.abs(ChronoUnit.WEEKS.between(date, today));
            return weeks.intValue();
        }
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public UserProfile getUser() {
        return user;
    }

    @JsonProperty
    public void setUser(UserProfile user) {
        this.user = user;
    }


}

