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
import java.io.FileWriter;
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
    File file = new File(filename + ".json");

    try {
        // Check if the file exists
        if (!file.exists()) {
            // Create the file and write an empty JSON array
            file.createNewFile();
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("[]"); // Write an empty JSON array
            }
        }

        // Load the contents of the file into the list
        Content[] contents = mapper.readValue(file, Content[].class);
        System.out.println("content length in load: "+ contents.length);
        for (Content c : contents) {
            list.add(c);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}
    
}
    


