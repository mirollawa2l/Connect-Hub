/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Common;

import Content_Creation.Backend.Content;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author mirol
 */
public class test {
    public static void main(String[] args) {
        // Create a Content object
        Content c = new Content("123", "Hi", "path", LocalDateTime.now());
        
        // Create a Json instance
        Json je = new Json();
        
        // Save the Content object as JSON
        je.save("text", c);
    }
}
