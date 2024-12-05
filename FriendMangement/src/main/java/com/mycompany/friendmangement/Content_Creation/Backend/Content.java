/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Content_Creation.Backend;

import java.time.LocalDateTime;

/**
 *
 * @author mirol
 */
public class Content {
protected String contentId;
protected String authorId;
protected String content;
protected String imagePath;
protected LocalDateTime timestamp;
protected boolean isStory;

public Content()
{
    
}

    public Content(String authorId, String content, String imagePath, LocalDateTime timestamp) {
        this.authorId = authorId;
        this.content = content;
        this.imagePath = imagePath;
        this.timestamp = timestamp;
        this.isStory=false;
    }

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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
       public boolean isExpired() {
           if(isStory)
        return timestamp.plusHours(24).isBefore(LocalDateTime.now());
           else return false;
    }





    
}

