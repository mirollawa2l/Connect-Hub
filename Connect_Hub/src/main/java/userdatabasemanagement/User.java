package userdatabasemanagement;

import Content_Creation.Backend.Post;
import Groups_Backend.Group;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

public  class User implements UserInterface {

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
    private ArrayList<String> friendRequests;
    private ArrayList<String> sentFriendRequests;
    private ArrayList<String> blockList;

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

    public User(String id, String email, String username, String password, String profilePhotoPath, String coverPhotoPath, String bio, String dateOfBirth, String status, ArrayList<String> friends,ArrayList<String>friendRequests,ArrayList<String>sentFriendRequests,ArrayList<String>blockList) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.profilePhotoPath = profilePhotoPath;
        this.coverPhotoPath = coverPhotoPath;
        this.bio = bio;
        this.friendRequests=friendRequests;
        this.status = status;
        this.friends = friends;
        this.dateOfBirth = dateOfBirth;
        this.sentFriendRequests=sentFriendRequests;
        this.blockList=blockList;
    }
    
// Factory Method
   public static User create(String userId,  String email, String username,String hashedPassword, String profilePhotoPath, String coverPhotoPath, String bio,  String dateOfBirth ,String status ,ArrayList<String> friends ,ArrayList<String>friendRequests,ArrayList<String>sentFriendRequests,ArrayList<String>blockList) {
        return new User(userId,  email, username,hashedPassword, profilePhotoPath, coverPhotoPath, bio, dateOfBirth,status , friends ,friendRequests,sentFriendRequests,blockList);
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

    public ArrayList<String> getFriendRequests() {
        return friendRequests;
    }

    public ArrayList<String> getSentFriendRequests() {
        return sentFriendRequests;
    }

    public ArrayList<String> getBlockList() {
        return blockList;
    }

    public void setBlockList(ArrayList<String> blockList) {
        this.blockList = blockList;
    }
    
    }