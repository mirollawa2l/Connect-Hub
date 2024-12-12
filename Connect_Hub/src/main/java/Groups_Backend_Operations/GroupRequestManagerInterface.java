/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Groups_Backend_Operations;

import Groups_Backend.Group;
import java.util.ArrayList;
import userdatabasemanagement.User;

/**
 *
 * @author sherrygirguis
 */
public interface GroupRequestManagerInterface {
    public abstract ArrayList<GroupRequest> getRequests() ;
    public abstract boolean isRequest(User user, ArrayList<GroupRequest> requests);
    public abstract void acceptRequest(GroupRequest groupRequest);
    public abstract void declineRequest(GroupRequest groupRequest);
    public abstract void sendRequest(User user,Group group);
    public abstract GroupRequest getRequest(User user,String groupId);
    
}
