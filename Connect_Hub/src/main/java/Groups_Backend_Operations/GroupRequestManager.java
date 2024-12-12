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
    private GroupRequestManager requestManager;
    private GroupManager manager;
    private ArrayList<GroupRequest>requests=new ArrayList<>();

    public ArrayList<GroupRequest> getRequests() {
        return requests;
    }
    
    
    public boolean isRequest(User user, ArrayList<GroupRequest> requests) {
    for (GroupRequest request : requests) {
        if (request.getSender().equals(user.getId())) {
            return true;
        }
    }
    return false;
}
 
         public void acceptRequest(GroupRequest groupRequest){
            
             if(!manager.isMember(groupRequest.getSender(),groupRequest.getGroup())){
                       groupRequest.getGroup().getRequestedMembers().remove(groupRequest.getSender());
                       groupRequest.getGroup().getMembers().add(groupRequest.getSender());
                       manager.saveToFile(manager.getGroups());}
         }
      
        public void declineRequest(GroupRequest groupRequest){
            groupRequest.getGroup().getRequestedMembers().remove(groupRequest.getSender());
            manager.saveToFile(manager.getGroups());
        }
        
        public void  sendRequest(User user,Group group){
         ArrayList<User> members = group.getMembers();
         ArrayList<GroupRequest> requests = requestManager.getRequests();
    if (members != null) {
        // Add all users as potential suggestions
        for (Group g:manager.getGroups()){
             if (!manager.isMember(user,group)&& !requestManager.isRequest(user,requests)) {
                 GroupRequest groupRequest=new GroupRequest(group,user);
                 group.getRequestedMembers().add(user);
                 requests.add(groupRequest);
                 manager.saveToFile(manager.getGroups());
             }}}
        
        
        }
       public GroupRequest  getRequest(User user,String groupId){
               for(GroupRequest request:getRequests()){
                   if(user.getId().equals(request.getSender().getId()))
                       return request;
               }
                
                return null;
  
}}

    
   