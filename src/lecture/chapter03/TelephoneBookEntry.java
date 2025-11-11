package lecture.chapter03;

public class TelephoneBookEntry {

    private String firstName;
    private String surName;
    private String phoneNumber;

    public TelephoneBookEntry(String firstName, String surName, String phoneNumber) {
        this.firstName = firstName;
        this.surName = surName;
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurName() {
        return surName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return "First name: " + firstName + "\n"
             + "Surname: " + surName + "\n"
             + "Phone: " + phoneNumber;
    }
}