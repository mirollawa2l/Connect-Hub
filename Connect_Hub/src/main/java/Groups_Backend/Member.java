/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Groups_Backend;


import Groups_Backend_Operations.GroupRequestManager;
import Notifications.NotificationManager;
import java.time.LocalDate;
import java.util.ArrayList;
import userdatabasemanagement.CurrentUser;
import userdatabasemanagement.User;

/**
 *
 * @author sherrygirguis
 */
public class Member extends User {
    
    private GroupManager manager;
    private GroupRequestManager requestManager;
    private Group thisGroup;
    private User thisUser;
   

    public Member() {
        manager=new GroupManager();
        requestManager= new GroupRequestManager();
        thisUser = CurrentUser.getInstance().getCurrentUser();
        thisGroup = CurrentGroup.getInstance().getCurrentGroup();
                
            }
        
    

    public Member(String id, String email, String username, String password, String dateOfBirth, String status) {
        super(id, email, username, password, dateOfBirth, status);
    }

    public Member(String id, String email, String username, String password, String profilePhotoPath, String coverPhotoPath, String bio, String dateOfBirth, String status, ArrayList<String> friends, ArrayList<String> friendRequests, ArrayList<String> sentFriendRequests, ArrayList<String> blockList, ArrayList<String> deletedGroups, ArrayList<String> groups) {
        super(id, email, username, password, profilePhotoPath, coverPhotoPath, bio, dateOfBirth, status, friends, friendRequests, sentFriendRequests, blockList, deletedGroups, groups);
    }
    
    
    
    public void leaveGroup(Group group,User user){
       manager.getGroup(group.getGroupId()).getMembers().remove(user);
       if(manager.isSubAdmin(user, group))
           manager.getGroup(group.getGroupId()).getSubAdmins().remove(user);
       //   NotificationManager.getInstance().addNotification(user.getUsername()+" leave group", , user, type, true);
            
       }
          
    
    public void requestToJoinGroup(Group group,User user){
         requestManager.sendRequest(user,group);
    
    }
}
