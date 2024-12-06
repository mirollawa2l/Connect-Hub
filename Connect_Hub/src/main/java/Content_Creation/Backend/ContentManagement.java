/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Content_Creation.Backend;

import Common.Json;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author mirol
 */
public class ContentManagement {
     private  final String fileName = "contentDatabase";
     private ArrayList<Content> contents;
     private Json j;
     public ContentManagement()
     {
          j=new Json();
         contents=new ArrayList<>();
           save();
           load();
        
     }
     
     public void save()
     {
         for(Content c:contents)
         {
           j.save(fileName,c);
         }
     }
     public void load()
     {
         j.load(fileName,contents);
     }
     
     public void addContent(Content c)
     {
           if(c!=null)
         {
             contents.add(c);
             save();
         }
         else
             System.out.println("Content to add is null!");
     }
     
     public void deleteContent(Content c)
     {
         if(c!=null)
         {
             contents.remove(c);
             save();
         }
        
         else
             System.out.println("Content to delete is null!");
     }
     
     public Content getContent(String contentId)
     {
         for(Content c:contents)
         {
             if(c.contentId.equals(contentId))
                 return c;
         }
         System.out.println("Content with this id doesn't exist!");
         return null;
     }
     
       public void deleteExpiredStories() {
       for(Content c:contents)
       {
           if(c.isExpired())
               deleteContent(c);
       }
       save();
    }

    public ArrayList<Content> getContents() {
        return contents;
    }
     

    
}
