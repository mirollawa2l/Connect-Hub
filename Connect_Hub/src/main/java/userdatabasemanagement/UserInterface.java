/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package userdatabasemanagement;

import java.util.ArrayList;

/**
 *
 * @author mirol
 */
public interface UserInterface {

    public abstract String getProfilePhotoPath();

    public abstract void setProfilePhotoPath(String profilePhotoPath);

    public abstract String getCoverPhotoPath();

    public abstract void setCoverPhotoPath(String coverPhotoPath);

    public abstract String getBio();

    public abstract void setBio(String bio);

    public abstract void setEmail(String email);

    public abstract void setUsername(String username);

    public abstract void setPassword(String password);

    public abstract void setStatus(String status);

    public abstract String getId();

    public abstract String getEmail();

    public abstract String getUsername();

    public abstract String getPassword();

    public abstract String getDateOfBirth();

    public abstract String getStatus();

    public abstract ArrayList<String> getFriends();

    public abstract ArrayList<String> getFriendRequests();

    public abstract ArrayList<String> getSentFriendRequests();

}
