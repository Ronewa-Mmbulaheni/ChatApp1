package chatapp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Attribution:
 *   Google, 2024. Gson User Guide.
 *   https://github.com/google/gson/blob/main/UserGuide.md
 */
public class MessageStorage {
    private final String filePath = "messages.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // load old, add new, save back
    public void saveMessage(Message message) {
        List<Message> existing = loadMessagesList();
        existing.add(message);
        writeMessages(existing);
    }

    // read file, return list (or empty if error)
    private List<Message> loadMessagesList() {
        try (Reader reader = new FileReader(filePath)) {
            Type listType = new TypeToken<List<Message>>() {}.getType();
            List<Message> messages = gson.fromJson(reader, listType);
            return messages != null ? messages : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    // convert list to array because rubric wants arrays
    public Message[] loadMessages() {
        List<Message> list = loadMessagesList();
        return list.toArray(new Message[0]);
    }

    // save list to file
    private void writeMessages(List<Message> messages) {
        try (Writer writer = new FileWriter(filePath)) {
            gson.toJson(messages, writer);
        } catch (IOException e) {
            System.out.println("Error saving to JSON: " + e.getMessage());
        }
    }

    // find hash, delete it, save, return the text for the printout
    public String deleteByHash(String hash) {
        List<Message> messages = loadMessagesList();
        for (int i = 0; i < messages.size(); i++) {
            if (messages.get(i).getMessageHash().equals(hash)) {
                String deletedText = messages.get(i).getMessageText();
                messages.remove(i);
                writeMessages(messages);
                return deletedText;
            }
        }
        return null;
    }
}