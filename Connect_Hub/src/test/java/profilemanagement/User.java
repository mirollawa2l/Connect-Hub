/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package profilemanagement;

import java.util.List;

/**
 *
 * @author HP
 */
public class User {
    private  String userId;
    private  String username;
    private  String email;
    private String hashedPassword;
    private String profilePhotoPath;
    private String coverPhotoPath;
    private String bio;
    private String status;
    private List <String> friends;
    
    
    //default constructor for jackson
    public User() {
    }

    
    //  constructor
    

    public User(String userId, String username, String email, String hashedPassword, String profilePhotoPath, String coverPhotoPath, String bio, String status, List<String> friends) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.profilePhotoPath = profilePhotoPath;
        this.coverPhotoPath = coverPhotoPath;
        this.bio = bio;
        this.status = status;
        this.friends = friends;
    }

    // Factory Method
   public static User create(String userId, String username, String email, String hashedPassword, String profilePhotoPath, String coverPhotoPath, String bio, String status , List<String> friends) {
        return new User(userId, username, email, hashedPassword, profilePhotoPath, coverPhotoPath, bio, status , friends);
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    // method to add friend
    public void addFriend(String frirndId){
        friends.add(frirndId);
    }
    // method to remove friend
    public void removeFriend(String friendId){
        friends.remove(friendId);
    }

    @Override
    public String toString() {
        return "User{" + "userId=" + userId + ", username=" + username + ", email=" + email + ", hashedPassword=" + hashedPassword + ", profilePhotoPath=" + profilePhotoPath + ", coverPhotoPath=" + coverPhotoPath + ", bio=" + bio + ", status=" + status + ", friends=" + friends + '}';
    }
    
}
