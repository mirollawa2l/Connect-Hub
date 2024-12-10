/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Groups_Backend;

import Content_Creation.Backend.Post;
import java.time.LocalDate;
import java.util.ArrayList;
import userdatabasemanagement.User;

/**
 *
 * @author mirol
 */
public class SubAdmin extends User{
  
 private ArrayList <Group> groupsToManage;
 

    public SubAdmin() {
    }

    public SubAdmin(ArrayList<Group> groupsToManage, String id, String email, String username, String password, LocalDate dateOfBirth, String status) {
        super(id, email, username, password, dateOfBirth, status);
        this.groupsToManage = groupsToManage;
    }

    public SubAdmin(ArrayList<Group> groupsToManage, String id, String email, String username, String password, String profilePhotoPath, String coverPhotoPath, String bio, String dateOfBirth, String status, ArrayList<String> friends, ArrayList<String> friendRequests, ArrayList<String> sentFriendRequests) {
        super(id, email, username, password, profilePhotoPath, coverPhotoPath, bio, dateOfBirth, status, friends, friendRequests, sentFriendRequests);
        this.groupsToManage = groupsToManage;
    }
    
    public void approveNewMember(User user){}

    public void declineNewMember(){}
    
    public void removeUser(){}
    
    public void editPosts(Post post){}
    
    public void deletePost(Post post){}
    
  // cannot change the primary admin (exclude the user from the editing,deleting,removing admin)
    // cannot add,delete,edit,remove other admins 
    
    
}