/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Content_Creation.Backend;

import static java.lang.String.valueOf;
import java.time.LocalDateTime;

/**
 *
 * @author mirol
 */
public class Story extends Content{
    private int storyCount=0;
   
    public Story()
    {
        this.isStory=true;
        this.contentId="S"+valueOf(++storyCount);
    }
    public Story(String authorId, String content, String imagePath, LocalDateTime timestamp) {
        super(authorId, content, imagePath, timestamp);
        this.isStory=true;
        this.contentId="S"+valueOf(++storyCount);
    }
   
    
}
