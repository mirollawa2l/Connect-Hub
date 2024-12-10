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
public class Admin extends SubAdmin{
         
    public Admin() {
    }

    public Admin(ArrayList<Group> groupsToManage, String id, String email, String username, String password, LocalDate dateOfBirth, String status) {
        super(groupsToManage, id, email, username, password, dateOfBirth, status);
    }

    public Admin(ArrayList<Group> groupsToManage, String id, String email, String username, String password, String profilePhotoPath, String coverPhotoPath, String bio, String dateOfBirth, String status, ArrayList<String> friends, ArrayList<String> friendRequests, ArrayList<String> sentFriendRequests) {
        super(groupsToManage, id, email, username, password, profilePhotoPath, coverPhotoPath, bio, dateOfBirth, status, friends, friendRequests, sentFriendRequests);
    }
    
   public void promoteMember(User user){}
   
   public void demoteMember(User user){}
   
   public void deleteGroup(Group group){}
}
