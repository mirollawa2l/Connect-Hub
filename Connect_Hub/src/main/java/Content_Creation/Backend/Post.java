package Content_Creation.Backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Post that extends Content. Each post is assigned a unique ID
 * with a prefix "P".
 */
@JsonIgnoreProperties(ignoreUnknown = true) // Ignores unknown fields like 'story'
public class Post extends Content {

    private static int postCount = 0; // Shared across all instances
    private final static String filename = "postsDatabase.json";

    // Default constructor to initialize a post with a unique ID
    public Post() {
        super(); // Call the default constructor of Content
        this.contentId = "P" + (++postCount); // Increment static counter and assign unique ID
    }

    // Constructor with parameters
    public Post(String authorId, String content, String imagePath, LocalDateTime timestamp) {
        super(authorId, content, imagePath, timestamp); // Call Content constructor
        this.contentId = "P" + (++postCount); // Increment and assign contentId
    }

    // Static method to set the post count to a specific value (useful for persistence)
    public static void setPostCount(int count) {
        postCount = count;
    }

    // Static method to get the current post count (useful for persistence)
    public static int getPostCount() {
        return postCount;
    }
public void saveToFile(ArrayList<Post> posts) {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // For pretty printing JSON

    File file = new File(filename);

    try {
        // Ensure the file exists or create a new one
        if (!file.exists()) {
            file.createNewFile();
        }

        // Overwrite the file with the new list of posts
        objectMapper.writeValue(file, posts);
        System.out.println("Posts successfully saved to: " + filename);
    } catch (IOException e) {
        e.printStackTrace();
    }
}
    public List<Post> loadFromFile() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        File file = new File(filename);
        List<Post> posts = new ArrayList<>();

        try {
            if (file.exists() && file.length() > 0) {
                posts = objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, Post.class));
                System.out.println("Posts loaded successfully from: " + filename);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return posts;
    }
}
