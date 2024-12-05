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
public class Post extends Content{
    private int postCount=0;
    
    public Post()
    {
         this.contentId="P"+valueOf(++postCount);
    }
    
    public Post(String authorId, String content, String imagePath, LocalDateTime timestamp) {
        super(authorId, content, imagePath, timestamp);
        this.contentId="P"+valueOf(++postCount);
    }
    
}
