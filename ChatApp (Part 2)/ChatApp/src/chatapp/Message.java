package chatapp;

import java.util.Random;

public class Message {
    
    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;
    private String flag;

    // Default Constructor
    public Message(int messageNumber, String recipient, String messageText) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = generateID();
        this.messageHash = createHash();
        this.flag = "Pending";
    }
    
    // Overloaded Constructor (for deterministic testing)
    public Message(int messageNumber, String recipient, String messageText, String forcedID) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = forcedID;
        this.messageHash = createHash();
        this.flag = "Pending";
    }

    private String generateID() {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder(10);
        sb.append(rand.nextInt(9) + 1); // First digit 1-9
        for (int i = 0; i < 9; i++) {
            sb.append(rand.nextInt(10)); // Remaining 9 digits 0-9
        }
        return sb.toString();
    }

    public boolean checkMessageID() {
        return messageID != null && messageID.length() == 10;
    }

    public boolean checkRecipientCell() {
        // Must start with + and have 10 to 12 digits total
        return recipient.matches("^\\+[0-9]{10,12}$");
    }

    public boolean checkMessageLength() {
        return messageText.length() <= 250 && !messageText.isEmpty();
    }

    public String createHash() {
        if (messageID == null || messageID.length() < 2) return "";
        String firstTwo = messageID.substring(0, 2);
        
        String[] words = messageText.trim().split("\\s+");
        String firstWord = "";
        String lastWord = "";
        
        if (words.length > 0 && !words[0].isEmpty()) {
            firstWord = words[0].replaceAll("[^A-Za-z]", "").toUpperCase();
            lastWord = words[words.length - 1].replaceAll("[^A-Za-z]", "").toUpperCase();
        }
        return firstTwo + ":" + messageNumber + ":" + firstWord + lastWord;
    }

    public String printMessage() {
        return "Message ID: " + messageID + "\n"
             + "Message Hash: " + messageHash + "\n"
             + "Recipient: " + recipient + "\n"
             + "Message: " + messageText;
    }

    // Getters and Setters
    public String getMessageID() { return messageID; }
    public int getMessageNumber() { return messageNumber; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
    public String getMessageHash() { return messageHash; }
    public String getFlag() { return flag; }
    public void setFlag(String flag) { this.flag = flag; }
}