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
public class SubAdmin extends User {

    protected String groupId;
    protected GroupManager manager;

    public SubAdmin() {

    }

    public SubAdmin(String groupId, String id, String email, String username, String password, LocalDate dateOfBirth, String status) {
        super(id, email, username, password, dateOfBirth, status);
        this.groupId = groupId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void approveNewMember(User user) {
        manager.getGroup(groupId).getRequestedMembers().remove(user);
        manager.getGroup(groupId).getMembers().add(user);
    }

    public void declineNewMember(User user) {
        manager.getGroup(groupId).getRequestedMembers().remove(user);
    }

    public void removeUser(User user) {
        if (user == manager.getGroup(groupId).getAdmin()) {
            System.out.println("SubAdmin Can't remove primary Admin");
        } else if (manager.isSubAdmin(user, manager.getGroup(groupId))) {
            System.out.println("SubAdmin Can't remove another subAdmin");
        } else if (manager.isMember(user, manager.getGroup(groupId))) {
            manager.getGroup(groupId).getMembers().remove(user);

        }
    }

    public void editPosts(Post post) {

    }

    public void deletePost(Post post) {
        manager.getGroup(groupId).getPosts().remove(post);
    }

    public void addPost(Post post) {
        manager.getGroup(groupId).getPosts().add(post);

    }

    // cannot change the primary admin (exclude the user from the editing,deleting,removing admin)
    // cannot add,delete,edit,remove other admins 
}
