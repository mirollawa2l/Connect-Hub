/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Groups_Backend;


import Groups_Backend_Operations.GroupRequestManager;
import userdatabasemanagement.User;

/**
 *
 * @author sherrygirguis
 */
public class Member extends User {
    
    private GroupManager manager;
    private GroupRequestManager requestManager;
    
    public void leaveGroup(Group group,User user){
       manager.getGroup(group.getGroupId()).getMembers().remove(user);
       if(manager.isSubAdmin(user, group))
           manager.getGroup(group.getGroupId()).getSubAdmins().remove(user);
       
            
       }
          
    
    public void requestToJoinGroup(Group group,User user){
         requestManager.sendRequest(user,group);
    
    }
}
