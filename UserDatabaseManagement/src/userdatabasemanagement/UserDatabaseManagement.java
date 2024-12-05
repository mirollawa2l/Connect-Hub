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

/**
 *
 * @author Yara
 */
public class UserDatabaseManagement {

   private static final String USERS_FILE= "users.json";
      private ArrayList<User> users;
      private ObjectMapper objectMapper;
      private int idCount=0;
    
    public UserDatabaseManagement(){
        objectMapper = new ObjectMapper();
        users = loadUsers();
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
    public User getUserByEmail(String email) {
    for (User user : users) {
        if (user.getEmail().equals(email)) {
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
            objectMapper.writeValue(new File(USERS_FILE), users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    public void saveUser(User user) {
      
        if (isUserIdFound(user.getId())) { //user already exist
            System.out.println("user already exists");
            return;
        }
        users.add(user);
        saveDatabase();   
    }
    public void updateStatus(String email, String newStatus){
       
        for(User user: users){
            if(user.getEmail().equals(email)){
                user.setStatus(newStatus);
                saveDatabase();
                System.out.println("hello from update status");
                break;
            }
        }
        
    }
     

    public static void main(String[] args) {
       AccountManagment managmentFrame= new AccountManagment();
       managmentFrame.setVisible(true);
       managmentFrame.setLocationRelativeTo(null );
        
    }
}


