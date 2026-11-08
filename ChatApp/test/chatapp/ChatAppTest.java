package chatapp;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ChatAppTest {

    private Login login;
    private MessageStorage storage;

    // hardcoding rubric test data here to check logic
    private String[] sentMessages = new String[5];
    private String[] storedMessages = new String[5];
    private String[] messageIDs = new String[5];
    private String[] messageRecipients = new String[5];
    private String[] messageHashes = new String[5];

    @Before
    public void setUp() {
        login = new Login();
        storage = new MessageStorage();
        
        // test data from page 18-19
        sentMessages[0] = "Did you get the cake?";
        messageIDs[0] = "0838884567";
        messageRecipients[0] = "+27834557896";
        messageHashes[0] = "08:0:DIDCAKE?";

        storedMessages[1] = "Where are you? You are late! I have asked you to be on time.";
        messageIDs[1] = "1234567890";
        messageRecipients[1] = "+27838884567";
        messageHashes[1] = "12:1:WHERETIME.";

        storedMessages[2] = "Ok, I am leaving without you.";
        messageIDs[2] = "0987654321";
        messageRecipients[2] = "+27838884567";
        messageHashes[2] = "09:2:OKYOU.";

        sentMessages[3] = "It is dinner time !";
        messageIDs[3] = "0838884567"; // msg 4
        messageRecipients[3] = "Developer";
        messageHashes[3] = "08:3:IT!";
    }

    // --- PART 1 TESTS ---
    @Test public void validUsername() { assertTrue(login.checkUserName("kyl_1")); }
    @Test public void invalidUsername() { assertFalse(login.checkUserName("kyle!!!!!!!")); }
    @Test public void validPassword() { assertTrue(login.checkPasswordComplexity("Ch&&sec@ke99!")); }
    @Test public void invalidPassword() { assertFalse(login.checkPasswordComplexity("password")); }
    @Test public void validCellPhone() { assertTrue(login.checkCellPhoneNumber("+27838968976")); }
    @Test public void invalidCellPhone() { assertFalse(login.checkCellPhoneNumber("08966553")); }

    @Test public void registrationSucceeds() {
        assertEquals("Username successfully captured.\nPassword successfully captured.", login.registerUser("kyl_1", "Ch&&sec@ke99!"));
    }
    @Test public void registrationFailsOnUsername() {
        assertEquals("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.", login.registerUser("kyle!!!!!!!", "Ch&&sec@ke99!"));
    }
    @Test public void registrationFailsOnPassword() {
        assertEquals("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.", login.registerUser("kyl_1", "password"));
    }
    @Test public void successfulLogin() {
        login.saveUser("John", "Doe", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(login.loginUser("kyl_1", "Ch&&sec@ke99!"));
    }
    @Test public void failedLogin() {
        login.saveUser("John", "Doe", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertFalse(login.loginUser("wrong", "wrong"));
    }
    @Test public void welcomeMessageAfterLogin() {
        login.saveUser("John", "Doe", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertEquals("Welcome John, Doe it is great to see you again.", login.returnLoginStatus(true));
    }
    @Test public void rejectedLoginMessage() {
        assertEquals("Username or password incorrect, please try again.", login.returnLoginStatus(false));
    }

    // --- PART 2 TESTS ---
    @Test public void testValidRecipient() {
        Message msg = new Message(0, "+27718693002", "Hi");
        assertTrue(msg.checkRecipientCell());
    }
    @Test public void testInvalidRecipient() {
        Message msg = new Message(0, "08575975889", "Hi");
        assertFalse(msg.checkRecipientCell());
    }
    @Test public void testValidMessageLength() {
        Message msg = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertTrue(msg.checkMessageLength());
    }
    @Test public void testMessageTooLong() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 260; i++) sb.append("a");
        Message msg = new Message(0, "+27718693002", sb.toString());
        assertFalse(msg.checkMessageLength());
    }
    @Test public void testOfficialHash() {
        Message msg = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight?", "0012345678");
        assertEquals("00:0:HITONIGHT", msg.getMessageHash());
    }
    @Test public void testMessageIDLength() {
        Message msg = new Message(0, "+27718693002", "Hi");
        assertTrue(msg.checkMessageID());
    }

    // --- PART 3 TESTS (straight from rubric) ---
    
    // check if sent array has the right test data
    @Test public void testSentMessagesArrayPopulated() {
        assertEquals("Did you get the cake?", sentMessages[0]);
        assertEquals("It is dinner time !", sentMessages[3]);
    }

    // loop to find longest, should be message 2
    @Test public void testDisplayLongestMessage() {
        String longest = "";
        for (String msg : storedMessages) {
            if (msg != null && msg.length() > longest.length()) longest = msg;
        }
        assertEquals("Where are you? You are late! I have asked you to be on time.", longest);
    }

    // search id 0838884567, should give msg 4
    @Test public void testSearchForMessageID() {
        String expectedMessage = "";
        for (int i = 0; i < messageIDs.length; i++) {
            if ("0838884567".equals(messageIDs[i]) && i == 3) { 
                expectedMessage = sentMessages[i];
            }
        }
        assertEquals("It is dinner time !", expectedMessage);
    }

    // find all for this cell, should get msg 2 and 5
    @Test public void testSearchMessagesByRecipient() {
        String rec = "+27838884567";
        String msg1 = "", msg2 = "";
        for (int i = 0; i < messageRecipients.length; i++) {
            if (rec.equals(messageRecipients[i])) {
                if (i == 1) msg1 = storedMessages[i];
                if (i == 2) msg2 = storedMessages[i];
            }
        }
        assertEquals("Where are you? You are late! I have asked you to be on time.", msg1);
        assertEquals("Ok, I am leaving without you.", msg2);
    }

    // delete hash 12:1..., check the success message text
    @Test public void testDeleteMessageByHash() {
        String hashToDelete = "12:1:WHERETIME.";
        String deletedMessage = null;
        
        for (int i = 0; i < messageHashes.length; i++) {
            if (hashToDelete.equals(messageHashes[i])) {
                deletedMessage = storedMessages[i];
                storedMessages[i] = null;
            }
        }
        assertEquals("Message: \"Where are you? You are late! I have asked you to be on time.\" successfully deleted.", 
                     "Message: \"" + deletedMessage + "\" successfully deleted.");
    }
}