package chatapp;

import java.util.regex.Pattern;

public class Login {
    // remember index positions: 0=first, 1=last, 2=user, 3=pass, 4=cell
    private static final int FIRST_NAME = 0;
    private static final int LAST_NAME  = 1;
    private static final int USERNAME   = 2;
    private static final int PASSWORD   = 3;
    private static final int CELL       = 4;

    // regex: must start with +27 and have exactly 9 digits after
    private static final Pattern SA_MOBILE = Pattern.compile("^\\+27[0-9]{9}$");
    private final String[] savedUser = new String[5];

    // username must have "_" and be max 5 chars
    public boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    // password needs 8 chars, 1 capital, 1 number, 1 special
    public boolean checkPasswordComplexity(String password) {
        if (password.length() < 8) return false;
        boolean hasCapital = false, hasDigit = false, hasSymbol = false;
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isUpperCase(c)) hasCapital = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else if (!Character.isLetterOrDigit(c)) hasSymbol = true;
        }
        return hasCapital && hasDigit && hasSymbol;
    }

    // checks if cell matches the regex above
    public boolean checkCellPhoneNumber(String cellPhone) {
        return SA_MOBILE.matcher(cellPhone).matches();
    }

    // returns the right message for registration
    public String registerUser(String username, String password) {
        if (!checkUserName(username)) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        return "Username successfully captured.\nPassword successfully captured.";
    }

    // save details into the array
    public void saveUser(String firstName, String lastName, String username, String password, String cellPhone) {
        savedUser[FIRST_NAME] = firstName;
        savedUser[LAST_NAME]  = lastName;
        savedUser[USERNAME]   = username;
        savedUser[PASSWORD]   = password;
        savedUser[CELL]       = cellPhone;
    }

    // check if array data matches what they typed
    public boolean loginUser(String username, String password) {
        if (savedUser[USERNAME] == null) return false;
        return savedUser[USERNAME].equals(username) && savedUser[PASSWORD].equals(password);
    }

    // return welcome or error message
    public String returnLoginStatus(boolean loggedIn) {
        if (loggedIn) {
            return "Welcome " + savedUser[FIRST_NAME] + ", " + savedUser[LAST_NAME] + " it is great to see you again.";
        }
        return "Username or password incorrect, please try again.";
    }

    // need this for part 3 reports
    public String getFullName() {
        return savedUser[FIRST_NAME] + " " + savedUser[LAST_NAME];
    }
}