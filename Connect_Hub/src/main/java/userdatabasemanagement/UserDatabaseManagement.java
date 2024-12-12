/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package userdatabasemanagement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Yara
 */
public class UserDatabaseManagement {

   private static final String USERS_FILE= "users.json";
      private ArrayList<User> users;
      private ObjectMapper objectMapper;
    
    public UserDatabaseManagement() {
    objectMapper = new ObjectMapper();
    users = loadUsers();

    if (users == null) { 
        users = new ArrayList<>(); // Initialize an empty list if the file is empty
    }
}
    
    public ArrayList<User> loadUsers(){
        
      
        ArrayList<User> users = new ArrayList<>();
        File file = new File(USERS_FILE);

        if (file.exists()) {
            try {
                //serialization
                users = objectMapper.readValue(file, new TypeReference<ArrayList<User>>() {});
            } catch (IOException e) {
                System.out.println("Error reading users.json: " + e.getMessage());
            }
        }
        return users;
        
    }
    public boolean isUserIdFound(String id){
         for (User user : loadUsers()){
             if(user.getId().equals(id))
                 return true;
         }
         return false;
    }
    public boolean isUser(String email, String encrypedPassword){
         for (User user : loadUsers()){
             if(user.getEmail().equals(email)&& user.getPassword().equals(encrypedPassword))
                 return true;
         }
         return false;
    }
    public User getUser(String userId) {
    for (User user : users) {
        if (user.getId().equals(userId)) {
            return user; // Return the user
        }
    }
    return null; // User not found
}
    public User getUserByUsername(String username) {
    for (User user : users) {
        if (user.getUsername().equals(username)) {
            return user; // Return the user
        }
    }
    return null; // User not found
}
    
    public ArrayList<User> listUsers() {
    return users; 
}
    
 private void saveDatabase() {
    try {
        System.out.println("Saving users to file: " + users);
        objectMapper.writeValue(new File(USERS_FILE), users);
    } catch (IOException e) {
        System.out.println("Error saving users: " + e.getMessage());
    }
}

    public void saveUser(User user) {
    users = loadUsers(); // Ensure the latest data is loaded before adding

    if (users == null) { 
        users = new ArrayList<>(); // Initialize if users list is null
    }

    if (isUserIdFound(user.getId())) { // Check if user already exists
        System.out.println("User already exists");
        return;
    }

    users.add(user); // Add new user to the list
    saveDatabase();  // Save the updated list back to the file
}
    public User getUserByEmail(String email)
    {
        for(User u:users)
        {
            if(u.getEmail().equals(email))
                return u;
        }
        return null;
    }

    public static void main(String[] args) {
       AccountManagment managmentFrame= new AccountManagment();
       managmentFrame.setVisible(true);
       managmentFrame.setLocationRelativeTo(null );
        
    }
    public void updateStatus(String userId,String newStatus) throws IOException{
      
    // Load the users from the JSON file
    users = loadUsers();
    
    System.out.println("Users before update: " + users.toString());

    // Flag to check if user is found
    boolean userFound = false;

    // Find the user and update their status
    for (User user : users) {
        if (user.getId().equals(userId)) {
            user.setStatus(newStatus); // Update the user's status
            userFound = true;
            break;
        }
    }

    if (!userFound) {
        throw new IllegalArgumentException("User not found: " + userId);
    }

     System.out.println("Users after update: " + users.toString());

    // Save the updated list of users back to the JSON file
    saveDatabase();
    System.out.println("Status updated successfully for user ID: " + userId);
}}


