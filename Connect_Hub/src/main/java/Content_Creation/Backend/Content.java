package Content_Creation.Backend;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Custom deserializer for LocalDateTime



public abstract class Content implements ContentCreation{

    protected String contentId;
    protected String authorId;
    protected String content;
    protected String imagePath;

    // Use custom deserializer for timestamp
   // @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime timestamp;

    protected boolean isStory;
    protected boolean isExpired;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Default constructor (required for Jackson)
    public Content() {}

    // Parameterized constructor
    public Content(String authorId, String content, String imagePath, LocalDateTime timestamp) {
        this.authorId = authorId;
        this.content = content;
        this.imagePath = imagePath;
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        this.timestamp = timestamp.format(formatter);

this.timestamp=timestamp;
        this.isStory = false;
    }

    // Getters and Setters
    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

   public LocalDateTime getTimestamp(){
//       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//       return LocalDateTime.parse(this.timestamp, formatter);
return this.timestamp;
   }

    public void setTimestamp(LocalDateTime timestamp) {
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        this.timestamp = timestamp.format(formatter);
this.timestamp=timestamp;
    }

    public boolean isStory() {
        return isStory;
    }

    public void setIsStory(boolean isStory) {
        this.isStory = isStory;
    }

    public boolean isExpired() {
        if (isStory) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//            isExpired = LocalDateTime.parse(this.timestamp, formatter).plusHours(24).isBefore(LocalDateTime.now());

            isExpired = timestamp.plusHours(24).isBefore(LocalDateTime.now());

        } else {
            isExpired = false;
        }
        return isExpired;
    }
    
    

    // Utility to format the timestamp (if needed)
//    public String getFormattedTimestamp() {
//        return timestamp != null ? timestamp.format(FORMATTER) : null;
//    }
}