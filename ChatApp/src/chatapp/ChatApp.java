package chatapp;

import java.util.Scanner;

public class ChatApp {
    private static final Scanner read = new Scanner(System.in);
    private static final Login login = new Login();
    private static final MessageStorage storage = new MessageStorage();
    private static int totalMessagesSent = 0;

    // part 3 wants arrays, keeping index in sync
    private static String[] sentMessages = new String[50];
    private static String[] disregardedMessages = new String[50];
    private static String[] storedMessages = new String[50];
    private static String[] messageHashes = new String[50];
    private static String[] messageIDs = new String[50];
    private static String[] messageRecipients = new String[50];
    
    private static int arrayIndex = 0;

    public static void main(String[] args) {
        System.out.println("==================================");
        System.out.println("           REGISTRATION           ");
        System.out.println("==================================");

        String firstName = ask("Enter your first name: ");
        String lastName  = ask("Enter your last name: ");
        String username  = askUsername();
        String password  = askPassword();
        String cellPhone = askCellPhone();

        login.saveUser(firstName, lastName, username, password, cellPhone);
        System.out.println(login.registerUser(username, password));

        if (startLogin()) {
            runMenu();
        }
        read.close();
    }

    // just prints and trims input
    private static String ask(String message) {
        System.out.print(message);
        return read.nextLine().trim();
    }

    // keep asking till username is good
    private static String askUsername() {
        while (true) {
            String username = ask("Enter your username: ");
            if (login.checkUserName(username)) return username;
            System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.");
        }
    }

    // keep asking till password is good
    private static String askPassword() {
        while (true) {
            System.out.print("Enter your password: ");
            String password = read.nextLine();
            if (login.checkPasswordComplexity(password)) return password;
            System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
        }
    }

    // keep asking till cell is good
    private static String askCellPhone() {
        while (true) {
            String cellPhone = ask("Enter your cell phone number: ");
            if (login.checkCellPhoneNumber(cellPhone)) return cellPhone;
            System.out.println("Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.");
        }
    }

    // loop login until they get it right
    private static boolean startLogin() {
        while (true) {
            System.out.println("\n===========================");
            System.out.println("           LOGIN           ");
            System.out.println("===========================");
            String username = ask("Enter your username: ");
            System.out.print("Enter your password: ");
            String password = read.nextLine();

            boolean loggedIn = login.loginUser(username, password);
            System.out.println(login.returnLoginStatus(loggedIn));
            if (loggedIn) return true;
        }
    }

    // main menu loop
    private static void runMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\nWelcome to QuickChat.");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.println("4) Stored Messages"); // part 3
            String choice = ask("Choose an option: ");

            if (choice.equals("1")) {
                sendMessages();
            } else if (choice.equals("2")) {
                System.out.println("Coming Soon.");
            } else if (choice.equals("3")) {
                running = false;
                System.out.println("Goodbye!");
            } else if (choice.equals("4")) {
                storedMessagesMenu();
            } else {
                System.out.println("Invalid option. Please choose 1-4.");
            }
        }
    }

    // ask how many, then loop that many times
    private static void sendMessages() {
        int count = 0;
        while (true) {
            try {
                count = Integer.parseInt(ask("How many messages do you wish to enter? "));
                if (count > 0) break;
                System.out.println("Please enter a number greater than zero.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid number.");
            }
        }

        for (int i = 0; i < count; i++) {
            System.out.println("\n--- Message " + (i + 1) + " of " + count + " ---");
            
            // don't move on until cell is right
            String recipient = "";
            while (true) {
                recipient = ask("Recipient cell number (e.g. +27838968976): ");
                Message temp = new Message(i, recipient, "test");
                if (temp.checkRecipientCell()) break;
                System.out.println("Cell phone number incorrectly formatted or does not contain an international code.");
            }

            // don't move on until message length is good
            String text = "";
            while (true) {
                text = ask("Message text: ");
                Message temp = new Message(i, recipient, text);
                if (temp.checkMessageLength()) break;
                System.out.println("Message exceeds 250 characters or is empty.");
            }

            Message msg = new Message(i, recipient, text);
            
            System.out.println("\n1) Send Message\n2) Store Message\n3) Disregard Message");
            String action = ask("Choose: ");

            // save to the right array based on choice
            if (action.equals("1")) {
                msg.setFlag("Sent");
                sentMessages[arrayIndex] = msg.getMessageText();
                messageIDs[arrayIndex] = msg.getMessageID();
                messageHashes[arrayIndex] = msg.getMessageHash();
                messageRecipients[arrayIndex] = msg.getRecipient();
                arrayIndex++;
                totalMessagesSent++;
                System.out.println("Message sent.");
            } else if (action.equals("2")) {
                msg.setFlag("Stored");
                storedMessages[arrayIndex] = msg.getMessageText();
                messageIDs[arrayIndex] = msg.getMessageID();
                messageHashes[arrayIndex] = msg.getMessageHash();
                messageRecipients[arrayIndex] = msg.getRecipient();
                arrayIndex++;
                storage.saveMessage(msg);
                totalMessagesSent++;
                System.out.println("Message stored.");
            } else {
                msg.setFlag("Disregard");
                disregardedMessages[arrayIndex] = msg.getMessageText();
                arrayIndex++;
                System.out.println("Message disregarded.");
            }
        }
        System.out.println("Total messages processed: " + totalMessagesSent);
    }

    // part 3 sub-menu
    private static void storedMessagesMenu() {
        boolean viewing = true;
        while (viewing) {
            System.out.println("\n=== STORED MESSAGES ===");
            System.out.println("1) Display sender and recipient");
            System.out.println("2) Display longest message");
            System.out.println("3) Search by Message ID");
            System.out.println("4) Search by recipient");
            System.out.println("5) Delete by Message Hash");
            System.out.println("6) Full Report");
            System.out.println("7) Back");
            String choice = ask("Choose: ");

            if (choice.equals("1")) {
                System.out.println("Sender: " + login.getFullName());
                for (int i = 0; i < storedMessages.length; i++) {
                    if (storedMessages[i] != null) {
                        System.out.println("Recipient: " + messageRecipients[i] + " | Message: " + storedMessages[i]);
                    }
                }
            } else if (choice.equals("2")) {
                String longest = "";
                for (String msg : storedMessages) {
                    if (msg != null && msg.length() > longest.length()) longest = msg;
                }
                System.out.println("Longest Message: " + longest);
            } else if (choice.equals("3")) {
                String id = ask("Enter Message ID: ");
                boolean found = false;
                for (int i = 0; i < messageIDs.length; i++) {
                    if (id.equals(messageIDs[i]) && storedMessages[i] != null) {
                        System.out.println("Recipient: " + messageRecipients[i] + " | Message: " + storedMessages[i]);
                        found = true;
                    }
                }
                if (!found) System.out.println("Not found.");
            } else if (choice.equals("4")) {
                String rec = ask("Enter Recipient: ");
                boolean found = false;
                for (int i = 0; i < messageRecipients.length; i++) {
                    if (rec.equals(messageRecipients[i]) && storedMessages[i] != null) {
                        System.out.println("Message: " + storedMessages[i]);
                        found = true;
                    }
                }
                if (!found) System.out.println("No messages found for this recipient.");
            } else if (choice.equals("5")) {
                String hash = ask("Enter Message Hash: ");
                String deleted = storage.deleteByHash(hash);
                if (deleted != null) {
                    System.out.println("Message: \"" + deleted + "\" successfully deleted.");
                    // also remove from arrays so report updates
                    for (int i = 0; i < messageHashes.length; i++) {
                        if (hash.equals(messageHashes[i])) {
                            storedMessages[i] = null;
                            messageHashes[i] = null;
                        }
                    }
                } else {
                    System.out.println("Hash not found.");
                }
            } else if (choice.equals("6")) {
                System.out.println("=== FULL REPORT ===");
                for (int i = 0; i < storedMessages.length; i++) {
                    if (storedMessages[i] != null) {
                        System.out.println("Hash: " + messageHashes[i] + " | Recipient: " + messageRecipients[i] + " | Message: " + storedMessages[i]);
                    }
                }
            } else if (choice.equals("7")) {
                viewing = false;
            } else {
                System.out.println("Invalid option. Please enter 1-7.");
            }
        }
    }
}