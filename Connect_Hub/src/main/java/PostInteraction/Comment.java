/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PostInteraction;

import java.time.LocalDateTime;

/**
 *
 * @author Yara
 */


public class Comment {
    private String authorId; // The user who made the comment
    private String text;     // The comment text
    private LocalDateTime timestamp;

    // Default constructor (required for JSON deserialization)
    public Comment() {}

    // Parameterized constructor
    public Comment(String authorId, String text, LocalDateTime timestamp) {
        this.authorId = authorId;
        this.text = text;
        this.timestamp = timestamp;
    }

    // Getter for authorId
    public String getAuthorId() {
        return authorId;
    }

    // Setter for authorId
    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    // Getter for text
    public String getText() {
        return text;
    }

    // Setter for text
    public void setText(String text) {
        this.text = text;
    }

    // Getter for timestamp
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    // Setter for timestamp
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "authorId='" + authorId + '\'' +
                ", text='" + text + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
