package Content_Creation.Backend;

import PostInteraction.Comment;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

// Custom deserializer for LocalDateTime



public abstract class Content implements ContentCreation {

    protected String contentId;
    protected String authorId;
    protected String content;
    protected String imagePath;

    private LocalDateTime timestamp;

    protected boolean isStory;
    protected boolean isExpired;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // New field to store comments
    private List<Comment> comments;

    // Default constructor (required for Jackson)
    public Content() {
        comments = new ArrayList<>(); // Initialize the comments list
    }

    // Parameterized constructor
    public Content(String authorId, String content, String imagePath, LocalDateTime timestamp) {
        this.authorId = authorId;
        this.content = content;
        this.imagePath = imagePath;
        this.timestamp = timestamp;
        this.isStory = false;
        this.comments = new ArrayList<>(); // Initialize the comments list
    }

    // Getters and Setters for comments
    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    // Other existing getters and setters...
    @Override
    public String getContentId() {
        return contentId;
    }

    @Override
    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    @Override
    public String getAuthorId() {
        return authorId;
    }

    @Override
    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String getImagePath() {
        return imagePath;
    }

    @Override
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    @Override
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean isStory() {
        return isStory;
    }

    @Override
    public void setIsStory(boolean isStory) {
        this.isStory = isStory;
    }

    @Override
    public boolean isExpired() {
        if (isStory) {
            isExpired = timestamp.plusHours(24).isBefore(LocalDateTime.now());
        } else {
            isExpired = false;
        }
        return isExpired;
    }
}
