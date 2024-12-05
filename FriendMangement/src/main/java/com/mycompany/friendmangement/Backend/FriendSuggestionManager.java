
package com.mycompany.friendmangement.Backend;

import java.util.ArrayList;


public class FriendSuggestionManager {
    private FriendSuggestion suggestion;
    private ArrayList< FriendSuggestion >ListOfSuggestions=new ArrayList<>();
    private ManageFriends friendsManager;
    private UserAccountManagement accountManager ;
   
   public FriendSuggestionManager(UserAccountManagement accountManager,ManageFriends friendsManager){
    this.accountManager=accountManager;
    this.friendsManager=friendsManager;
}
   
    public ArrayList<FriendSuggestion> generateSuggestions(){
        ArrayList <User> Suggested= new ArrayList<>();
       
        for (User user:accountManager.loadUsers()){
           for(User friend:friendsManager.getFriends()){
               if (user==friend)
                   Suggested.remove(user);
           }}
        for(User user:Suggested){
            suggestion=new FriendSuggestion(user);
        ListOfSuggestions.add(suggestion);}
        return ListOfSuggestions;
    }
    
    public FriendSuggestion sendSuggestion(User suggested){
        suggestion=new FriendSuggestion(suggested);
        ListOfSuggestions.add(suggestion);
        return suggestion;}
        
    public void acceptFriendSuggestion(FriendSuggestion suggestion){
        friendsManager.AddFriend(suggestion.getSuggested());
        ListOfSuggestions.remove(suggestion);

    }
       public void declineFriendSuggestion(FriendSuggestion suggestion){
        ListOfSuggestions.remove(suggestion);
    } 
       public ArrayList< FriendSuggestion >getListOfSuggestions(){
       return ListOfSuggestions;
       }
       
        public FriendSuggestion getSuggestion(String username){
            for(FriendSuggestion suggestion:ListOfSuggestions)
                if(username.equals(suggestion.getSuggested().getUsername()))
                    return suggestion;
            return null;
       
       }
      

}
