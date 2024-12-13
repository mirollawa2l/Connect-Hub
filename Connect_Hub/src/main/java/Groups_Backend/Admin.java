/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Groups_Backend;

import java.time.LocalDate;
import java.util.ArrayList;
import userdatabasemanagement.User;

/**
 *
 * @author mirol
 */
public class Admin extends SubAdmin {

    public Admin() {
    }

    public Admin(String id, String email, String username, String password, String dateOfBirth, String status) {
        super(id, email, username, password, dateOfBirth, status);
    }

    public Admin(String id, String email, String username, String password, String profilePhotoPath, String coverPhotoPath, String bio, String dateOfBirth, String status, ArrayList<String> friends, ArrayList<String> friendRequests, ArrayList<String> sentFriendRequests, ArrayList<String> blockList, ArrayList<String> deletedGroups, ArrayList<String> groups) {
        super(id, email, username, password, profilePhotoPath, coverPhotoPath, bio, dateOfBirth, status, friends, friendRequests, sentFriendRequests, blockList, deletedGroups, groups);
    }
    
    
    
    
    

    public void promoteMember(User user) {
        boolean flag = false;
        for (User u : manager.getGroup(groupId).getMembers()) {
            if (u.getId().equals(user.getId())) {
                manager.getGroup(groupId).getSubAdmins().add((SubAdmin) u);
                flag = true;
                 manager.save();
        manager.load();
            }
        }
        if (!flag) {
            System.out.println("User not found in members list");
        }
    }

    
    
    public void demoteMember(User user) {
        boolean flag = false;
        for (User u : manager.getGroup(groupId).getMembers()) {
            if (u.getId().equals(user.getId())) {
                manager.getGroup(groupId).getSubAdmins().remove((SubAdmin) u);
                flag = true;
                 manager.save();
        manager.load();
            }
        }
        if (!flag) {
            System.out.println("User not found in members list");
        }
         manager.save();
        manager.load();
    }

    public void deleteGroup(Group group) {
        manager.getGroups().remove(group);
        manager.save();
        manager.load();
    }

    @Override
    public void removeUser(User user) {
        if (manager.isMember(user, manager.getGroup(groupId))) {
            manager.getGroup(groupId).getMembers().remove(user);
            if (manager.isSubAdmin(user, manager.getGroup(groupId))) {
                manager.getGroup(groupId).getSubAdmins().remove(user);
            }

        }
    manager.save();
        manager.load();
    }
}
