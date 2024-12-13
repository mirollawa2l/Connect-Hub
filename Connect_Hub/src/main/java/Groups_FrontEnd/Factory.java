/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Groups_FrontEnd;

import Groups_Backend.CurrentGroup;
import Groups_Backend.Group;
import Groups_Backend.GroupManager;
import javax.swing.JFrame;
import userdatabasemanagement.User;

/**
 *
 * @author sherrygirguis
 */
public class Factory {
private GroupManager manager;
private Group thisGroup;
    public Factory(GroupManager manager) {
        this.manager = manager;
        thisGroup=  CurrentGroup.getInstance().getCurrentGroup();
    }
    
     public  JFrame createWindow (User user){
         if(manager.isSAdmin(user, thisGroup))
             return new AdminWindow();
         else if(manager.isSubAdmin(user, thisGroup))
             return new SubAdminWindow();
         else  if(manager.isMember(user, thisGroup))
             return new MemberWindow();
     else {
            throw new IllegalArgumentException("User role not recognized in this group.");
        }
     }

  
    
}
