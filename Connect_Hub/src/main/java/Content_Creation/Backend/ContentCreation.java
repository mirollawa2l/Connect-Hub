/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Content_Creation.Backend;

import java.time.LocalDateTime;

/**
 *
 * @author mirol
 */
public interface ContentCreation {

    public abstract String getContentId();

    public abstract void setContentId(String contentId);

    public abstract String getAuthorId();

    public abstract void setAuthorId(String authorId);

    public abstract String getContent();

    public abstract void setContent(String content);

    public abstract String getImagePath();

    public abstract void setImagePath(String imagePath);

    public abstract LocalDateTime getTimestamp();

    public abstract void setTimestamp(LocalDateTime timestamp);

    public abstract boolean isStory();

    public abstract void setIsStory(boolean isStory);

    public abstract boolean isExpired();
}
