# ChatApp

A Java console application built for a messaging assignment. It has two parts: user registration/login, and a QuickChat messaging menu that validates, sends, stores, or disregards messages. Stored messages are persisted to JSON.

## Features

**Part 1 — Registration & Login**
- Username validation: must contain `_` and be ≤ 5 characters (`kyl_1`)
- Password validation: ≥ 8 chars, at least one capital letter, one digit, one special character
- South African cell number validation: `+27` followed by 9 digits (`+27838968976`)
- Registration returns descriptive success/failure messages
- Login checks saved credentials and returns a personalised welcome or rejection message

**Part 2 — QuickChat**
- Validates recipient cell numbers (`+` followed by 10–12 digits)
- Enforces a 250-character message limit
- Generates a 10-digit message ID
- Builds a message hash in the format `XX:N:FIRSTLAST` (first two ID digits, message number, first + last word uppercased)
- Menu options: send, store (saved to `messages.json`), or disregard a message
- Tracks total messages processed

## Project Structure

```
src/chatapp/
  ChatApp.java        # Main class, console UI and menus
  Login.java          # Registration, validation, login logic
  Message.java        # Message model, ID/hash generation, validation
  MessageStorage.java # Gson-based JSON persistence
test/chatapp/
  ChatAppTest.java    # JUnit tests for Parts 1 and 2
messages.json         # Persisted stored messages
```

## Requirements

- Java (project targets JDK 26)
- Apache Ant / NetBeans (project is a NetBeans Ant project)
- Libraries on the classpath:
  - JUnit 4 / JUnit 5 (tests)
  - Hamcrest
  - Gson 2.10.1 (`com.google.gson`)
  - org.json (`json-20250517.jar`)

Classpath entries are configured in `nbproject/project.properties`.

## Build & Run

**In NetBeans:** Open the project and press Run (F6), or Build (F11).

**From the command line (after building):**

```bash
cd dist
java -jar "ChatApp.jar"
```

**Run the tests:**

```bash
ant test
```

## Usage

1. Register by entering first name, last name, username, password, and cell number.
2. Log in with the same credentials.
3. Choose from the QuickChat menu:
   - `1` — Send messages (enter a count, then each message's recipient and text; choose to send, store, or disregard)
   - `2` — Show recently sent messages (`Coming Soon`)
   - `3` — Quit

## Notes

- Stored messages are appended to `messages.json` in the working directory.
- The `Message` class has an overloaded constructor accepting a forced ID, used for deterministic hash testing.
- `messages.json` may contain test data; delete it to start fresh.
