package ru.kpfu.itis.gnt.validators;

import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class RegistrationFieldsValidator {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String gender;
    private String dateOfBirth;
    private String country;
    private String passwordConfirm;
    private String policyAgreement;
    private ArrayList<String> errorList;


    private static final String DATE_REGEX_PATTERN = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$";
    private static final String EMAIL_REGEX_PATTERN = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private static final String PASSWORD_REGEX_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";

    public RegistrationFieldsValidator(String firstName, String lastName, String email, String password, String passwordConfirm, String gender, String dateOfBirth, String country, String policyAgreement) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.country = country;
        this.policyAgreement = policyAgreement;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        errorList = new ArrayList<>();
        validateInputs();
    }

    public RegistrationFieldsValidator(String firstName, String lastName, String email,String gender,String dateOfBirth,String country) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.country = country;
        errorList = new ArrayList<>();

        validateUpdateInputs();
    }


    private void validateUpdateInputs(){
        checkFirstName();
        checkLastName();
        checkEmail();
        checkDateOfBirth();
        checkCountry();
        checkGender();
    }
    private void validateInputs() {
        checkFirstName();
        checkLastName();
        checkPassword();
        checkEmail();
        checkGender();
        checkDateOfBirth();
        checkCountry();
        checkPolicyAgreement();
    }

    public ArrayList<String> getErrorList() {
        return errorList;
    }

    private void checkFirstName() {
        if (checkIfFieldIsEmpty(firstName)) {
            errorList.add("First name field is empty!");
        }
    }

    private void checkLastName() {
        if (checkIfFieldIsEmpty(lastName)) {
            errorList.add("Last name field is empty!");
        }
    }

    private void checkEmail() {
        if (checkIfFieldIsEmpty(email)) {
            errorList.add("Email field is empty!");
        } else if (countPattern(email, EMAIL_REGEX_PATTERN) == 0) {
            errorList.add("Wrong email format input!");
        }
    }

    private void checkPassword() {
        if (checkIfFieldIsEmpty(password)) {
            errorList.add("No password provided provided!");
        } else if (!Objects.equals(password, passwordConfirm)) {
            errorList.add("Passwords are not the same!");
        } else if (countPattern(password, PASSWORD_REGEX_PATTERN) == 0) {
            errorList.add("Wrong password value! Password should contain at least 8 characters, at least 1 symbol and 1 number.");
        }
    }

    private void checkGender() {
        if (checkIfFieldIsEmpty(gender)) {
            errorList.add("No gender selected!");
        }
    }

    private void checkDateOfBirth() {
        if (checkIfFieldIsEmpty(gender)) {
            errorList.add("Date of birth is not selected!");
        } else if (countPattern(dateOfBirth, DATE_REGEX_PATTERN) == 0) {
            errorList.add("Wrong date format!");
        }
    }

    private void checkCountry() {
        if (checkIfFieldIsEmpty(country)) {
            errorList.add("No country selected!");
        }
    }

    private void checkPolicyAgreement() {
        if (checkIfFieldIsEmpty(policyAgreement)) {
            errorList.add("Policy agreement field is not checked!");
        }
    }

    private boolean checkIfFieldIsEmpty(String field) {
        return field == null || field.isEmpty() || field.equals("null");
    }

    private int countPattern(String input, String regex) {
        Matcher matcher
                = Pattern.compile(String.valueOf(regex))
                .matcher(input);
        int res = 0;
        while (matcher.find()) {
            res++;
        }
        return res;
    }
}
