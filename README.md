
# ChatApp

## Project Description

ChatApp is a Java-based application that allows users to register and log in using their username and password. The application validates user information and displays appropriate messages for successful or unsuccessful registration and login attempts.

The project includes user registration, password complexity validation, South African cell phone number validation, and login authentication.

## Features



- User registration.
- Username validation.
- Password complexity validation.
- South African cell phone number validation.
- Login authentication.
- Successful and unsuccessful login messages.
- JUnit testing.

## Project Structure

```text
ChatApp/
├── Login.java
├── ChatApp.java
└── ChatAppTest.java
```

### Login.java

Contains the methods responsible for validating user details, registering users, saving user information, and checking login details.

### ChatApp.java

Contains the main application and console interface. It allows users to enter their details, register, and log in.

### ChatAppTest.java

Contains JUnit tests used to test the functionality of the Login class.

## Validation Rules

### Username

The username must:

- Contain an underscore (_).
- Be no more than five characters long.

Example of a valid username:

```text
kyl_1
```

### Password

The password must:

- Contain at least eight characters.
- Include a capital letter.
- Include a number.
- Include a special character.

Example of a valid password:

```text
Ch&&sec@ke99!
```

### Cell Phone Number

The cell phone number must use the South African international format:

```text
+27XXXXXXXXX
```

Example:

```text
+27838968976
```

## How to Run the Application

1. Open the project in a Java IDE such as NetBeans or IntelliJ IDEA.
2. Ensure that the files are located in the `chatapp` package.
3. Compile the Java files.
4. Run `ChatApp.java`.
5. Enter the required registration details.
6. Enter the username and password to log in.

## Testing

The project uses JUnit to test the Login class.

The tests cover:

- Valid and invalid usernames.
- Valid and invalid passwords.
- Valid and invalid cell phone numbers.
- Successful registration.
- Failed registration.
- Successful login.
- Failed login.
- Login status messages.

## Technologies Used

- Java
- JUnit
- Java Regular Expressions

## Author

Ronewa Mmbulaheni
