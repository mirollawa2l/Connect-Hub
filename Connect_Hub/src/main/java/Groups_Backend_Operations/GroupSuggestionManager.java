/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Groups_Backend_Operations;

import Groups_Backend.Group;
import Groups_Backend.GroupManager;
import java.util.ArrayList;
import javax.swing.JFrame;
import userdatabasemanagement.User;

/**
 *
 * @author sherrygirguis
 */
public class GroupSuggestionManager {
    
     private GroupManager manager;
     private GroupRequestManager requestManager;
    private ArrayList <GroupSuggestion> suggestions;

    public GroupSuggestionManager() {

        suggestions= new ArrayList<>();
         requestManager=new GroupRequestManager();
          manager=new GroupManager();
    }
  

   public ArrayList<GroupSuggestion> generateSuggestions(User user){
      
         suggestions.clear();
        if( requestManager.getRequests()!=null){
         ArrayList<GroupRequest> requests = requestManager.getRequests();
   
        for (Group g:manager.getGroups()){
             if (!manager.isMember(user,g)&&(!requestManager.isRequest(user,requests)||requests==null)) {
           GroupSuggestion suggestion=new GroupSuggestion(g);
            suggestions.add(suggestion);}}}
        else if(requestManager.getRequests()==null){for (Group g:manager.getGroups()){
             if (!manager.isMember(user,g)) {
           GroupSuggestion suggestion=new GroupSuggestion(g);
            suggestions.add(suggestion);}}
        
        }

        return suggestions;
    }


    
    public void acceptGroupSuggestion(GroupSuggestion suggestion,User user){
        requestManager.sendRequest(user,suggestion.getSuggested());
        suggestions.remove(suggestion);
        manager.save();

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

