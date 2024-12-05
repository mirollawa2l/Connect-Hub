/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package profilemanagement;

/**
 *
 * @author HP
 */
import java.time.LocalDateTime;

public class Post {
    private String postId;        // Unique ID for the post
    private String authorId;      // ID of the user who created the post
    private String content;       // Text content of the post
    private String timestamp;     // Timestamp when the post was created
    private String imagePath;     // Path to an optional attached image
    
    
// default constructor for jackson
    public Post() {
    }

    
    
    // Constructor
    public Post(String postId, String authorId, String content, String timestamp, String imagePath) {
        this.postId = postId;
        this.authorId = authorId;
        this.content = content;
        this.timestamp = timestamp;
        this.imagePath = imagePath;
    }

    // Factory method for creating a post with a generated timestamp
    public static Post create(String postId, String authorId, String content, String imagePath) {
        String timestamp = LocalDateTime.now().toString(); // Generate current timestamp
        return new Post(postId, authorId, content, timestamp, imagePath);
    }

    // Getters and setters
    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "Post{" +
                "postId='" + postId + '\'' +
                ", authorId='" + authorId + '\'' +
                ", content='" + content + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}