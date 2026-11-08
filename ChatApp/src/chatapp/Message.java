package chatapp;

import java.util.Random;

public class Message {
    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;
    private String flag;

    // normal constructor
    public Message(int messageNumber, String recipient, String messageText) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = generateID();
        this.messageHash = createHash();
        this.flag = "Pending";
    }

    // constructor for tests (forces a specific ID)
    public Message(int messageNumber, String recipient, String messageText, String forcedID) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = forcedID;
        this.messageHash = createHash();
        this.flag = "Pending";
    }

    // ID is 10 random digits, first one can't be 0
    private String generateID() {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder(10);
        sb.append(rand.nextInt(9) + 1);
        for (int i = 0; i < 9; i++) sb.append(rand.nextInt(10));
        return sb.toString();
    }

    // just check if ID is exactly 10 chars
    public boolean checkMessageID() {
        return messageID != null && messageID.length() == 10;
    }

    // recipient must start with + and have 10 to 12 digits
    public boolean checkRecipientCell() {
        return recipient.matches("^\\+[0-9]{10,12}$");
    }

    // message can't be empty, max 250 chars
    public boolean checkMessageLength() {
        return messageText.length() <= 250 && !messageText.isEmpty();
    }

    // hash = first 2 ID digits + : + msg num + : + first+last word (caps)
    public String createHash() {
        if (messageID == null || messageID.length() < 2) return "";
        String firstTwo = messageID.substring(0, 2);
        String[] words = messageText.trim().split("\\s+");
        String firstWord = "", lastWord = "";
        if (words.length > 0 && !words[0].isEmpty()) {
            firstWord = words[0].replaceAll("[^A-Za-z]", "").toUpperCase();
            lastWord  = words[words.length - 1].replaceAll("[^A-Za-z]", "").toUpperCase();
        }
        return firstTwo + ":" + messageNumber + ":" + firstWord + lastWord;
    }

    // standard print out for the user
    public String printMessage() {
        return "Message ID: " + messageID + "\n" +
               "Message Hash: " + messageHash + "\n" +
               "Recipient: " + recipient + "\n" +
               "Message: " + messageText;
    }

    // getters and setters
    public String getMessageID() { return messageID; }
    public int getMessageNumber() { return messageNumber; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
    public String getMessageHash() { return messageHash; }
    public String getFlag() { return flag; }
    public void setFlag(String flag) { this.flag = flag; }
}