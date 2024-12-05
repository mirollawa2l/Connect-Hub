
package userdatabasemanagement;
import Content_Creation.Backend.Content;
import java.time.*; 
import java.util.ArrayList;


public class User {
    private String id;
    private String email;
    private String username;
    private String password;
    private String dateOfBirth;
    private String status;
    private String[] friends;  
    private ArrayList<Content> contents;  //content bta3 mirolla
    
    public User(){
        
        
        
    }
    
    public User(String id, String email, String username, String password, LocalDate dateOfBirth, String status) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth.toString();
        this.status = status;
 
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

     public String getDateOfBirth() {
        return dateOfBirth ;  
    }

    public String getStatus() {
        return status;
    }

    public String[] getFriends() {
        return friends;
    }

    
     
    
}
