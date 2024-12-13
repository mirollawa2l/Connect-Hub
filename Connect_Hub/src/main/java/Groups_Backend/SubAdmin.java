/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Groups_Backend;

import Content_Creation.Backend.Post;
import Groups_Backend_Operations.GroupRequest;
import Groups_Backend_Operations.GroupRequestManager;
import java.time.LocalDate;
import java.util.ArrayList;
import userdatabasemanagement.User;

/**
 *
 * @author mirol
 */
public class SubAdmin extends User {

    protected String groupId;
    protected GroupManager manager;
    protected GroupRequestManager requestManager;

    public SubAdmin() {
    }

    public SubAdmin(String id, String email, String username, String password, String dateOfBirth, String status) {
        super(id, email, username, password, dateOfBirth, status);
    }

    public SubAdmin(String id, String email, String username, String password, String profilePhotoPath, String coverPhotoPath, String bio, String dateOfBirth, String status, ArrayList<String> friends, ArrayList<String> friendRequests, ArrayList<String> sentFriendRequests, ArrayList<String> blockList, ArrayList<String> deletedGroups, ArrayList<String> groups) {
        super(id, email, username, password, profilePhotoPath, coverPhotoPath, bio, dateOfBirth, status, friends, friendRequests, sentFriendRequests, blockList, deletedGroups, groups);
    }

  
    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void approveNewMember(GroupRequest request) {
        if(!manager.isMember(request.getSender(), manager.getGroup(groupId)))
        requestManager.acceptRequest(request);
           
    }

    public void declineNewMember(GroupRequest request) {
        requestManager.declineRequest(request);
    
    }
 

    public void removeUser(User user) {
        if (user == manager.getGroup(groupId).getAdmin()) {
            System.out.println("SubAdmin Can't remove primary Admin");
        } else if (manager.isSubAdmin(user, manager.getGroup(groupId))) {
            System.out.println("SubAdmin Can't remove another subAdmin");
        } else if (manager.isMember(user, manager.getGroup(groupId))) {
            manager.getGroup(groupId).getMembers().remove(user);

        }
         manager.save();
        manager.load();
    }

    public void editPosts(Post post,Post newPost) {
        manager.getGroup(groupId).getPosts().remove(post);
        manager.getGroup(groupId).getPosts().add(newPost);
         manager.save();
        manager.load();
    }

    public void deletePost(Post post) {
        manager.getGroup(groupId).getPosts().remove(post);
        manager.save();
        manager.load(); 
    }

    public void addPost(Post post) {
        manager.getGroup(groupId).getPosts().add(post);
         manager.save();
        manager.load();

    }

    // cannot change the primary admin (exclude the user from the editing,deleting,removing admin)
    // cannot add,delete,edit,remove other admins 
}
