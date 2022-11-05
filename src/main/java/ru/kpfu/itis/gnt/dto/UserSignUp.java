package ru.kpfu.itis.gnt.dto;

import java.util.Date;

public class UserSignUp {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String gender;
    private String country;
    private String dateOfBirth;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getGender() {
        return gender;
    }

    public String getCountry() {
        return country;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }
}
