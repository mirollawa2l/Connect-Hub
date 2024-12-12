/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Groups_Backend_Operations;

import Groups_Backend.Group;
import Groups_Backend.GroupManager;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import userdatabasemanagement.User;

/**
 *
 * @author sherrygirguis
 */
public class GroupSuggestionManager {
    
private GroupManager manager;
     private GroupRequestManager requestManager;
    private ArrayList <GroupSuggestion> suggestions= new ArrayList<>();
  

   public ArrayList<GroupSuggestion> generateSuggestions(Group group,User user){
      
         suggestions.clear();
         ArrayList<User> members = group.getMembers();
         ArrayList<GroupRequest> requests = requestManager.getRequests();
    if (members != null) {
        // Add all users as potential suggestions
        for (Group g:manager.getGroups()){
             if (!g.getGroupId().equals(group.getGroupId()) && !manager.isMember(user,group)&& !requestManager.isRequest(user,requests)) {
           GroupSuggestion suggestion=new GroupSuggestion(group);
            suggestions.add(suggestion);}}}
        return suggestions;
    }


    
    public void acceptGroupSuggestion(GroupSuggestion suggestion,User user){
        requestManager.sendRequest(user,suggestion.getSuggested());
        suggestions.remove(suggestion);
        manager.saveToFile(manager.getGroups());

    }
       
       public ArrayList< GroupSuggestion >getSuggestions(){
       return suggestions;
       }
       
        public GroupSuggestion getSuggestion(Group group){
            for(GroupSuggestion suggestion:suggestions)
                if(group.getGroupId().equals(suggestion.getSuggested()))
                    return suggestion;
            return null;
       
       }
       
}

