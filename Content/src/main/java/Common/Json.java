package Common;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author mirol
 */

import Content_Creation.Backend.Content;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
public class Json {
 
  public void save(String filename,Object obj)

  {
      ObjectMapper mapper = new ObjectMapper();

       
        // Write JSON to file
        try {
            mapper.writeValue(new File(filename+".json"), obj);
            System.out.println("JSON file created successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load(String filename, ArrayList<Content> list) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Content c = mapper.readValue(new File(filename + ".json"), Content.class);
            list.add(c);
        }catch (IOException e) {
            e.printStackTrace();
        }
    } 

    
}
    


