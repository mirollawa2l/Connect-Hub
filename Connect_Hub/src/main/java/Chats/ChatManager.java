/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Chats;

/**
 *
 * @author HP
 */
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import userdatabasemanagement.User;


import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.text.SimpleDateFormat;

public class ChatManager {
    private static ChatManager instance;
    private List<Chat> messageList;  // Stores messages in memory
    private String CHAT_FILE="chats.json";  // Path to the JSON file
     private ObjectMapper objectMapper;
     private Set<String> chattedUsers;
    // Private constructor to prevent instantiation
    ChatManager() {
        messageList = new ArrayList<>();
        chattedUsers= new HashSet<>();
       objectMapper=new ObjectMapper();
       objectMapper.registerModule(new JavaTimeModule());
        messageList=loadChats();
        System.out.println("Message List in constructor: "+messageList);
    }

    // Get the singleton instance
    public static ChatManager getInstance() {
        if (instance == null) {
            instance = new ChatManager();
        }
        return instance;
    }
//
//    // Load messages from the JSON file into memory
//    public ArrayList<Chat> loadChats() {
//        File file = new File(CHAT_FILE);
//        if (file.exists()) {
//            try {
//                CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Chat.class);
//                return objectMapper.readValue(file, listType);
//             //   notifications = objectMapper.readValue(file, new TypeReference<List<Notification>>() {});
//            } catch (IOException e) {
//               
//                System.err.println("Error loading Chats: " + e.getMessage());
//            }
//        } return new ArrayList<>();
//    }

    
    // Load messages from the JSON file into memory

    // Load data from a JSON file
    public ArrayList<Chat> loadChats() {
        objectMapper = getObjectMapper(); // Use configured ObjectMapper

        ArrayList<Chat> stories = new ArrayList<>();
        File file = new File(CHAT_FILE);

        try {
            // Check if the file exists and has content
            if (!file.exists() || file.length() == 0) {
                // If file is empty or does not exist, create it with an empty list
                System.out.println("File does not exist or is empty, creating an empty file.");
                file.createNewFile();
                objectMapper.writeValue(file, new ArrayList<Chat>()); // Initialize with an empty list
            }

            // Print the file content for debugging purposes
            String fileContent = new String(Files.readAllBytes(file.toPath()));
            System.out.println("File content before deserialization: " + fileContent);

            // Check if file content is valid JSON
            if (!fileContent.trim().isEmpty()) {
                // Deserialize the file contents
                stories = objectMapper.readValue(
                    file,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Chat.class)
                );
                System.out.println("Data loaded from file: " + CHAT_FILE);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return stories;
    }



    
    // Save notifications to the JSON file
    private void saveChats() {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(CHAT_FILE), messageList);
        } catch (IOException e) {
            System.err.println("Error saving chats: " + e.getMessage());
        }
    }
    // Write a new message to the list and save to the JSON file
    public void writeMessage(String sender, String receiver, String content) {
      //  String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        Chat newMessage = new Chat(sender, receiver, content);
        messageList.add(newMessage);
        
        saveChats();  // Save the updated list to the file
    }
    
    
    
    
       private ObjectMapper getObjectMapper() {
        // Configure the ObjectMapper with JavaTimeModule for LocalDateTime support
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // Pretty-print JSON
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // Use ISO-8601 format
        return objectMapper;
    }


    // Get the chat history between two users
    public List<Chat> getChatHistory(String user1, String user2) {
        List<Chat> chatHistory = new ArrayList<>();
        System.out.println("Message List: "+messageList);
        for (Chat message : messageList) {
            if ((message.getSenderId().equals(user1) && message.getReceiverId().equals(user2)) ||
                (message.getSenderId().equals(user2) && message.getReceiverId().equals(user1))) {
                chatHistory.add(message);
            }
        }
        return chatHistory;
    }
    
    
    
    

    // Get the list of all messages (for debugging purposes)
    public List<Chat> getAllMessages() {
        return messageList;
    }

    public Set<String> getChattedUsers() {
        return chattedUsers;
    }
    
}