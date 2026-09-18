package chatapp;

import java.util.Scanner;

public class ChatApp {

    private static final Scanner read = new Scanner(System.in);
    private static final Login login = new Login();

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

        startLogin();
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

    private static void startLogin() {
        boolean loggedIn = false;
        while (!loggedIn) {
            System.out.println();
            System.out.println("===========================");
            System.out.println("           LOGIN           ");
            System.out.println("===========================");
            String username = ask("Enter your username: ");
            System.out.print("Enter your password: ");
            String password = read.nextLine();

            loggedIn = login.loginUser(username, password);
            System.out.println(login.returnLoginStatus(loggedIn));
        }
    }
}