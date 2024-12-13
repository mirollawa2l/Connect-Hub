/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Groups_Backend_Operations;

import Groups_Backend.Group;
import Groups_Backend.GroupManager;
import java.util.ArrayList;
import userdatabasemanagement.User;

/**
 *
 * @author sherrygirguis
 */
public class GroupRequestManager {

    private GroupManager manager;
    private ArrayList<GroupRequest> requests;
    private ArrayList<User> members;
      

    public GroupRequestManager() {
        requests = new ArrayList<>();
        members = new ArrayList<>();
        manager=new GroupManager();
    }
//    
//
//    public ArrayList<GroupRequest> getRequests() {
//        return requests;
//    }
//
//    public boolean isRequest(User user, ArrayList<GroupRequest> requests) {
//        for (GroupRequest request : requests) {
//            if (request.getSender().equals(user.getId())) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public void acceptRequest(GroupRequest groupRequest) {
//
//        if (!manager.isMember(groupRequest.getSender(), groupRequest.getGroup())) {
//            groupRequest.getGroup().getRequestedMembers().remove(groupRequest.getSender());
//            groupRequest.getGroup().getMembers().add(groupRequest.getSender());
//            manager.save();
//            manager.load();
//
//        }
//    }
//
//    public void declineRequest(GroupRequest groupRequest) {
//        groupRequest.getGroup().getRequestedMembers().remove(groupRequest.getSender());
//        manager.save();
//        manager.load();
//    }
//
//    public void sendRequest(User user, Group group) {
//         members = group.getMembers();
//        if (members != null) {
//            // Add all users as potential suggestions
//            for (Group g : manager.getGroups()) {
//                if (!manager.isMember(user, group) && !isRequest(user, requests)) {
//                    GroupRequest groupRequest = new GroupRequest(group, user);
//                    group.getRequestedMembers().add(user);
//                    requests.add(groupRequest);
//                    manager.save();
//                }
//            }
//        }
//
//    }
//
//    public GroupRequest getRequest(User user, String groupId) {
//        for (GroupRequest request : getRequests()) {
//            if (user.getId().equals(request.getSender().getId())) {
//                return request;
//            }
//        }
//
//        return null;
//
//    }
   
 

    public ArrayList<GroupRequest> getRequests() {
        return requests;
    }

    public boolean isRequest(User user, Group group) {
        for (GroupRequest request : requests) {
            if (request.getSender().equals(user) && request.getGroup().equals(group)) {
                return true;
            }
        }
        return false;
    }
public boolean requestInGroup(User user,Group group){
   for(User member:group.getRequestedMembers())
       if(user.getId().equals(member.getId()))
           return true;
       return false;}
    

    public void acceptRequest(GroupRequest groupRequest) {
        if (!manager.isMember(groupRequest.getSender(), groupRequest.getGroup())) {
            groupRequest.getGroup().getRequestedMembers().remove(groupRequest.getSender());
             groupRequest.getGroup().getMembers().add(groupRequest.getSender());
            manager.save();
        }
    }

    public void declineRequest(GroupRequest groupRequest) {
        groupRequest.getGroup().getRequestedMembers().remove(groupRequest.getSender());
        manager.save();
    }

    public void sendRequest(User user, Group group) {
         members = group.getMembers();
        if (members != null) {
            // Add all users as potential suggestions
            for (Group g : manager.getGroups()) {
                if (!manager.isMember(user, group) &&!requestInGroup(user,group) ) {
                    GroupRequest groupRequest = new GroupRequest(group, user);
                    group.getRequestedMembers().add(user);
                    requests.add(groupRequest);
                    manager.save();
                }
            }
        }}

    
        
        

    public GroupRequest getRequest(User user, String groupId) {
        for (GroupRequest request : getRequests()) {
            if (user.equals(request.getSender()) && groupId.equals(request.getGroup().getGroupId())) {
                return request;  }
        }
        return null;
    }
}
