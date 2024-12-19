package Content_Creation.Backend;

import PostInteraction.Comment;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class Post extends Content {

    private static int postCount = 0; // Shared across all instances
    private final static String filename = "postsDatabase.json";

    private List<String> likes; // List of user IDs who liked the post
    private List<Comment> comments; // List of comments on the post

    // Default constructor
    public Post() {
        super(); // Call the default constructor of Content
        this.contentId = "P" + (++postCount); // Increment static counter and assign unique ID
        this.likes = new ArrayList<>(); // Initialize likes list
        this.comments = new ArrayList<>(); // Initialize comments list
    }

    // Constructor with parameters
    public Post(String authorId, String content, String imagePath, LocalDateTime timestamp) {
        super(authorId, content, imagePath, timestamp); // Call Content constructor
        this.contentId = "P" + (++postCount); // Increment and assign contentId
        this.likes = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    // Static method to set the post count to a specific value (useful for persistence)
    public static void setPostCount(int count) {
        postCount = count;
    }

    // Static method to get the current post count (useful for persistence)
    public static int getPostCount() {
        return postCount;
    }

    // Getters and Setters
    public List<String> getLikes() {
        return likes;
    }

    public void setLikes(List<String> likes) {
        this.likes = likes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    // Add a like to the post and save it to the file
    public void addLike(String userId) {
        if (!likes.contains(userId)) {
            likes.add(userId);
            // After adding the like, save the updated posts list
            saveUpdatedPosts(); // Save updated posts to file
        }
    }

// Add a comment to the post and save it to the file
    public void addComment(Comment comment) {
        comments.add(comment);
        // After adding the comment, save the updated posts list
        saveUpdatedPosts(); // Save updated posts to file
    }

// Method to save updated posts list to the file
    private void saveUpdatedPosts() {
        ContentManagement contentManagement = new ContentManagement();
        contentManagement.save();  // Save the posts to the file
    }

    // Save to file
    public void saveToFile(List<Post> posts) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // For pretty printing JSON

        File file = new File(filename);

        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            objectMapper.writeValue(file, posts);
            System.out.println("Posts successfully saved to: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load from file
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
