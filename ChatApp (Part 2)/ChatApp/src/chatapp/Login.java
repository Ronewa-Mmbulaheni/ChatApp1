package chatapp;

import java.util.regex.Pattern;

public class Login {

    // Positions in the saved user array
    private static final int FIRST_NAME = 0;
    private static final int LAST_NAME  = 1;
    private static final int USERNAME   = 2;
    private static final int PASSWORD   = 3;
    private static final int CELL       = 4;

    // South African mobile number pattern: +27 followed by 9 digits.
    // Source: https://en.wikipedia.org/wiki/Telephone_numbers_in_South_Africa
    private static final Pattern SA_MOBILE = Pattern.compile("^\\+27[0-9]{9}$");

    private final String[] savedUser = new String[5];

    // Check the username
    public boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    // Check the password
    public boolean checkPasswordComplexity(String password) {
        if (password.length() < 8) {
            return false;
        }
        boolean hasCapital = false;
        boolean hasDigit = false;
        boolean hasSymbol = false;
        int i = 0;
        while (i < password.length()) {
            char c = password.charAt(i);
            if (Character.isUpperCase(c)) {
                hasCapital = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSymbol = true;
            }
            i++;
        }
        return hasCapital && hasDigit && hasSymbol;
    }

    // Check the cell phone number
    public boolean checkCellPhoneNumber(String cellPhone) {
        return SA_MOBILE.matcher(cellPhone).matches();
    }

    // Register the user and return the correct message
    public String registerUser(String username, String password) {
        if (!checkUserName(username)) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        return "Username successfully captured.\nPassword successfully captured.";
    }

    // Save the user details
    public void saveUser(String firstName, String lastName, String username,
                         String password, String cellPhone) {
        savedUser[FIRST_NAME] = firstName;
        savedUser[LAST_NAME]  = lastName;
        savedUser[USERNAME]   = username;
        savedUser[PASSWORD]   = password;
        savedUser[CELL]       = cellPhone;
    }

    // Check the login details
    public boolean loginUser(String username, String password) {
        if (savedUser[USERNAME] == null) {
            return false;
        }
        return savedUser[USERNAME].equals(username)
                && savedUser[PASSWORD].equals(password);
    }

    // Return the correct login message
    public String returnLoginStatus(boolean loggedIn) {
        if (loggedIn) {
            return "Welcome " + savedUser[FIRST_NAME] + ", " + savedUser[LAST_NAME]
                    + " it is great to see you again.";
        }
        return "Username or password incorrect, please try again.";
    }
}