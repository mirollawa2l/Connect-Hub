/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Chats;

/**
 *
 * @author HP
 */
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.*;

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
import java.util.*;
import java.text.SimpleDateFormat;

public class ChatManager {
    private static ChatManager instance;
    private List<Chat> messageList;  // Stores messages in memory
    private String CHAT_FILE="chats.json";  // Path to the JSON file
     private final ObjectMapper objectMapper;
     private Set<String> chattedUsers;
    // Private constructor to prevent instantiation
    private ChatManager() {
        messageList = new ArrayList<>();
        chattedUsers= new HashSet<>();
       objectMapper=new ObjectMapper();
       objectMapper.registerModule(new JavaTimeModule());
        messageList=loadChats();
    }

    // Get the singleton instance
    public static ChatManager getInstance() {
        if (instance == null) {
            instance = new ChatManager();
        }
        return instance;
    }

    // Load messages from the JSON file into memory
    public ArrayList<Chat> loadChats() {
        File file = new File(CHAT_FILE);
        if (file.exists()) {
            try {
                CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Chat.class);
                return objectMapper.readValue(file, listType);
             //   notifications = objectMapper.readValue(file, new TypeReference<List<Notification>>() {});
            } catch (IOException e) {
               
                System.err.println("Error loading Chats: " + e.getMessage());
            }
        } return new ArrayList<>();
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

    // Get the chat history between two users
    public List<Chat> getChatHistory(String user1, String user2) {
        List<Chat> chatHistory = new ArrayList<>();
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