package userdatabasemanagement;

import java.time.*;
import java.util.ArrayList;

public class User {

    private String id;
    private String email;
    private String username;
    private String password;
    private String profilePhotoPath;
    private String coverPhotoPath;
    private String bio;
    private String dateOfBirth;
    private String status;
    private ArrayList<String> friends;

    public User() {
    }

    
    public User(String id, String email, String username, String password, LocalDate dateOfBirth, String status) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth.toString();
        this.status = status;

    }

    
    public String getProfilePhotoPath() {
        return profilePhotoPath;
    }

    public void setProfilePhotoPath(String profilePhotoPath) {
        this.profilePhotoPath = profilePhotoPath;
    }

    public String getCoverPhotoPath() {
        return coverPhotoPath;
    }

    public void setCoverPhotoPath(String coverPhotoPath) {
        this.coverPhotoPath = coverPhotoPath;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
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
        return dateOfBirth;
    }

    public String getStatus() {
        return status;
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

}
