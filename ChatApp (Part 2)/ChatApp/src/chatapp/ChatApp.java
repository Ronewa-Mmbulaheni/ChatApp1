package chatapp;

import java.util.Scanner;

public class ChatApp {

    private static final Scanner read = new Scanner(System.in);
    private static final Login login = new Login();
    private static final MessageStorage storage = new MessageStorage();
    private static int totalMessagesSent = 0;

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

        // Only proceed to Part 2 if login is successful
        if (startLogin()) {
            runMenu();
        }
        read.close();
    }

    private static String ask(String message) {
        System.out.print(message);
        return read.nextLine().trim();
    }

    private static String askUsername() {
        while (true) {
            String username = ask("Enter your username: ");
            if (login.checkUserName(username)) {
                System.out.println("Username successfully captured.");
                return username;
            }
            System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.");
        }
    }

    private static String askPassword() {
        while (true) {
            System.out.print("Enter your password: ");
            String password = read.nextLine();
            if (login.checkPasswordComplexity(password)) {
                System.out.println("Password successfully captured.");
                return password;
            }
            System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
        }
    }

    private static String askCellPhone() {
        while (true) {
            String cellPhone = ask("Enter your cell phone number: ");
            if (login.checkCellPhoneNumber(cellPhone)) {
                System.out.println("Cell number successfully captured.");
                return cellPhone;
            }
            System.out.println("Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.");
        }
    }

    private static boolean startLogin() {
        boolean loggedIn = false;
        while (!loggedIn) {
            System.out.println("\n===========================");
            System.out.println("           LOGIN           ");
            System.out.println("===========================");
            String username = ask("Enter your username: ");
            System.out.print("Enter your password: ");
            String password = read.nextLine();

            loggedIn = login.loginUser(username, password);
            System.out.println(login.returnLoginStatus(loggedIn));
        }
        return loggedIn;
    }

    // ==========================================
    // PART 2: MESSAGING MENU
    // ==========================================

    private static void runMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\nWelcome to QuickChat.");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            String choice = ask("Choose an option: ");

            if (choice.equals("1")) {
                sendMessages();
            } else if (choice.equals("2")) {
                System.out.println("Coming Soon.");
            } else if (choice.equals("3")) {
                running = false;
                System.out.println("Goodbye!");
            } else {
                System.out.println("Invalid option. Please enter 1, 2, or 3.");
            }
        }
    }

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
            
            String recipient = "";
            while (true) {
                recipient = ask("Recipient cell number (e.g. +27718693002): ");
                Message temp = new Message(i, recipient, "test");
                if (temp.checkRecipientCell()) break;
                System.out.println("Cell phone number incorrectly formatted or does not contain an international code.");
            }

            String text = "";
            while (true) {
                text = ask("Message text: ");
                Message temp = new Message(i, recipient, text);
                if (temp.checkMessageLength()) break;
                System.out.println("Message exceeds 250 characters or is empty.");
            }

            Message msg = new Message(i, recipient, text);
            System.out.println("\n" + msg.printMessage());

            System.out.println("\n1) Send Message\n2) Store Message\n3) Disregard Message");
            String action = ask("Choose: ");

            if (action.equals("1")) {
                msg.setFlag("Sent");
                totalMessagesSent++;
                System.out.println("Message sent.");
            } else if (action.equals("2")) {
                msg.setFlag("Stored");
                storage.saveMessage(msg);
                totalMessagesSent++;
                System.out.println("Message stored.");
            } else {
                msg.setFlag("Disregard");
                System.out.println("Message disregarded.");
            }
        }
        System.out.println("\nTotal messages processed: " + totalMessagesSent);
    }
}