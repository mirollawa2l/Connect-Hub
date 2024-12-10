/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Content_Creation.Backend;

import static Constants.FileNames.STORY_FILE;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Story that extends Content.
 * Each story is assigned a unique ID with a prefix "S".
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Story extends Content {
    private static int storyCount = 0;

    // Default constructor to initialize a story with a unique ID
    public Story() {
        super(); // Call the default constructor of Content
        this.isStory = true; // Set the isStory flag to true for Story
        this.contentId = "S" + (++storyCount); // Increment static counter and assign unique ID
    }

    // Constructor with parameters
    public Story(String authorId, String content, String imagePath, LocalDateTime timestamp) {
        super(authorId, content, imagePath, timestamp); // Call Content constructor
        this.isStory = true; // Set the isStory flag to true for Story
        this.contentId = "S" + (++storyCount); // Increment and assign contentId
    }

    // Static method to set the story count to a specific value (useful for persistence)
    public static void setStoryCount(int count) {
        storyCount = count;
    }

    // Static method to get the current story count (useful for persistence)
    public static int getStoryCount() {
        return storyCount;
    }

    private ObjectMapper getObjectMapper() {
        // Configure the ObjectMapper with JavaTimeModule for LocalDateTime support
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // Pretty-print JSON
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // Use ISO-8601 format
        return objectMapper;
    }

   public void saveToFile(ArrayList<Story> stories) {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // For pretty printing JSON

    File file = new File(STORY_FILE);

    try {
        // Ensure the file exists or create a new one
        if (!file.exists()) {
            file.createNewFile();
        }

        // Overwrite the file with the new list of posts
        objectMapper.writeValue(file, stories);
        System.out.println("Posts successfully saved to: " + STORY_FILE);
    } catch (IOException e) {
        e.printStackTrace();
    }
   }

    // Load data from a JSON file
    public ArrayList<Story> loadFromFile() {
        ObjectMapper objectMapper = getObjectMapper(); // Use configured ObjectMapper
        ArrayList<Story> stories = new ArrayList<>();
        File file = new File(STORY_FILE);

        try {
            // Check if the file exists and has content
            if (!file.exists() || file.length() == 0) {
                // If file is empty or does not exist, create it with an empty list
                System.out.println("File does not exist or is empty, creating an empty file.");
                file.createNewFile();
                objectMapper.writeValue(file, new ArrayList<Story>()); // Initialize with an empty list
            }

            // Print the file content for debugging purposes
            String fileContent = new String(Files.readAllBytes(file.toPath()));
            System.out.println("File content before deserialization: " + fileContent);

            // Check if file content is valid JSON
            if (!fileContent.trim().isEmpty()) {
                // Deserialize the file contents
                stories = objectMapper.readValue(
                    file,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Story.class)
                );
                System.out.println("Data loaded from file: " + STORY_FILE);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return stories;
    }
}
