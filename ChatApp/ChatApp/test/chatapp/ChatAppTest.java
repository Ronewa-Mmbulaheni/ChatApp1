package chatapp;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ChatAppTest {

    private Login login;

    @Before
    public void setUp() {
        login = new Login();
    }

    // Username 
    @Test
    public void validUsername() {
        assertTrue(login.checkUserName("kyl_1"));
    }

    @Test
    public void invalidUsername() {
        assertFalse(login.checkUserName("kyle!!!!!!!"));
    }

    // Password 
    @Test
    public void validPassword() {
        assertTrue(login.checkPasswordComplexity("Ch&&sec@ke99!"));
    }

    @Test
    public void invalidPassword() {
        assertFalse(login.checkPasswordComplexity("password"));
    }

    // Cell phone 
    @Test
    public void validCellPhone() {
        assertTrue(login.checkCellPhoneNumber("+27838968976"));
    }

    @Test
    public void invalidCellPhone() {
        assertFalse(login.checkCellPhoneNumber("08966553"));
    }

    //  Register messages 
    @Test
    public void registrationSucceeds() {
        assertEquals("Username successfully captured.\nPassword successfully captured.",
                login.registerUser("kyl_1", "Ch&&sec@ke99!"));
    }

    @Test
    public void registrationFailsOnUsername() {
        assertEquals("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.",
                login.registerUser("kyle!!!!!!!", "Ch&&sec@ke99!"));
    }

    @Test
    public void registrationFailsOnPassword() {
        assertEquals("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.",
                login.registerUser("kyl_1", "password"));
    }

    //  Login authentication 
    @Test
    public void successfulLogin() {
        login.saveUser("John", "Doe", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(login.loginUser("kyl_1", "Ch&&sec@ke99!"));
    }

    @Test
    public void failedLogin() {
        login.saveUser("John", "Doe", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertFalse(login.loginUser("wrong", "wrong"));
    }

    //  Login messages 
    @Test
    public void welcomeMessageAfterLogin() {
        login.saveUser("John", "Doe", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertEquals("Welcome John, Doe it is great to see you again.",
                login.returnLoginStatus(true));
    }

    @Test
    public void rejectedLoginMessage() {
        assertEquals("Username or password incorrect, please try again.",
                login.returnLoginStatus(false));
    }
}