package Backend.NobbieFinal.model;

public class BabyName {
    private String name;
    private Gender gender;
    private int listingNumber;

    public BabyName(String name, Gender gender, int listingNumber) {
        this.name = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase(); //name always needs to start with a capital
        this.gender = gender;
        this.listingNumber = listingNumber;
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
}
