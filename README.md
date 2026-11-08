# ChatApp - Part 3 (Final POE)

## Overview
This is the final part of the ChatApp POE. Building on Parts 1 and 2, this version introduces a fully functional "Stored Messages" menu that uses **parallel arrays** to manage, search, and delete messages. It also reads and writes data to a JSON file using the Gson library.

## Features Implemented (Part 3 Rubric)

### 1. Parallel Arrays
The application uses parallel arrays to keep track of all message data without hard-coding:
- `sentMessages[]` - Stores messages that were sent.
- `disregardedMessages[]` - Stores messages that were discarded.
- `storedMessages[]` - Stores messages that were saved and written to JSON.
- `messageHashes[]` - Stores the generated hash for each message.
- `messageIDs[]` - Stores the unique 10-digit ID for each message.
- `messageRecipients[]` - Stores the recipient's cell number.

### 2. Stored Messages Menu (Option 4)
A fourth main menu option has been added. When selected, users can access the following sub-features:
- **Display sender and recipient:** Shows the current logged-in user and the recipients of all stored messages.
- **Display the longest stored message:** Searches the array and prints the message with the most characters.
- **Search by Message ID:** Finds a specific message and displays its recipient and content.
- **Search by recipient:** Finds *all* messages sent to a specific cell number.
- **Delete by Message Hash:** Removes a message from the JSON file and the arrays, printing a success message.
- **Full Report:** Displays the Hash, Recipient, and Message for every stored message.

### 3. JSON File Handling
- Messages are saved to `messages.json`.
- The application reads the JSON file into an array using `Gson` and displays it.
- **Attribution:** The Gson library is used for JSON parsing. Reference provided in `MessageStorage.java`.

## How to Run
1. Open the project in NetBeans or your preferred Java IDE.
2. Ensure the Gson library is added to your project classpath.
3. Run the `ChatApp.java` file in the `chatapp` package.
4. Follow the console prompts to register, log in, and access the menu.

## Unit Testing (JUnit)
The `ChatAppTest.java` file contains all required unit tests for Parts 1, 2, and 3. 

### Test Data Used 
The arrays and tests are populated using the exact test data provided in the rubric:
- **Message 1 (Sent):** `+27834557896`, "Did you get the cake?"
- **Message 2 (Stored):** `+27838884567`, "Where are you? You are late! I have asked you to be on time."
- **Message 3 (Disregard):** `+27834484567`, "Yohoooo, I am at your gate."
- **Message 4 (Sent):** `0838884567`, "It is dinner time !"
- **Message 5 (Stored):** `+27838884567`, "Ok, I am leaving without you."

### Part 3 Specific Tests
- `testSentMessagesArrayPopulated()`: Verifies the sent array holds the correct data.
- `testDisplayLongestMessage()`: Verifies the system finds the longest message correctly.
- `testSearchForMessageID()`: Verifies search by ID returns the correct message.
- `testSearchMessagesByRecipient()`: Verifies searching by recipient returns multiple messages.
- `testDeleteMessageByHash()`: Verifies the delete message outputs the exact success string required.
