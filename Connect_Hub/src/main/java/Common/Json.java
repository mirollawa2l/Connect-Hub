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
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
public class Json {
 
  public void save(String filename,Object obj)

  {
      ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

       
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
    mapper.registerModule(new JavaTimeModule()); // Register JavaTimeModule for deserialization

    File file = new File(filename + ".json");

    try {
        // Create the file if it doesn't exist and initialize it with an empty array
        if (!file.exists()) {
            if (file.createNewFile()) { // Ensure file is created successfully
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write("[]"); // Write an empty JSON array
                }
            } else {
                throw new IOException("Failed to create new file: " + file.getAbsolutePath());
            }
        }

        // Load the contents of the file into the list
        Content[] contents = mapper.readValue(file, Content[].class);

        System.out.println("Content length in load: " + (contents != null ? contents.length : 0));

        if (contents != null) {
            list.clear(); // Clear the list to avoid duplicates
            list.addAll(Arrays.asList(contents));
        }
    } catch (IOException e) {
        System.err.println("Error while loading content from file: " + e.getMessage());
        e.printStackTrace(); // Replace with a logger in production
    }
}
    
}
    


