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

    public void saveMessage(Message message) {
        List<Message> existing = loadMessages();
        existing.add(message);
        
        try (Writer writer = new FileWriter(filePath)) {
            gson.toJson(existing, writer);
        } catch (IOException e) {
            System.out.println("Error saving to JSON: " + e.getMessage());
        }
    }

    public List<Message> loadMessages() {
        try (Reader reader = new FileReader(filePath)) {
            Type listType = new TypeToken<List<Message>>() {}.getType();
            List<Message> messages = gson.fromJson(reader, listType);
            return messages != null ? messages : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>(); // Return empty if file doesn't exist yet
        }
    }
}