/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Groups_FrontEnd;

import Groups_Backend.Group;
import Groups_Backend.GroupManager;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import userdatabasemanagement.User;

/**
 *
 * @author sherrygirguis
 */
public class Factory {
private GroupManager manager;

    public Factory(GroupManager manager) {
        this.manager = manager;
    }
    
     public  JFrame createWindow (User user,Group group){
         if(manager.isSAdmin(user, group))
             return new AdminWindow();
         else if(manager.isSubAdmin(user, group))
             return new SubAdminWindow();
         else  if(manager.isMember(user, group))
             return new MemberWindow();
     else {
            return null;
         }
     }

  
    
}
