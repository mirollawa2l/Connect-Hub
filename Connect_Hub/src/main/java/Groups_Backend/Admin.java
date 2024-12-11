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

    public void promoteMember(User user) {
        boolean flag = false;
        for (User u : manager.getGroup(groupId).getMembers()) {
            if (u.getId().equals(user.getId())) {
                manager.getGroup(groupId).getSubAdmins().add((SubAdmin) u);
                flag = true;
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
            }
        }
        if (!flag) {
            System.out.println("User not found in members list");
        }
    }

    public void deleteGroup(Group group) {
        manager.getGroups().remove(group);
    }

    @Override
    public void removeUser(User user) {
        if (manager.isMember(user, manager.getGroup(groupId))) {
            manager.getGroup(groupId).getMembers().remove(user);
            if (manager.isSubAdmin(user, manager.getGroup(groupId))) {
                manager.getGroup(groupId).getSubAdmins().remove(user);
            }

        }

    }
}
