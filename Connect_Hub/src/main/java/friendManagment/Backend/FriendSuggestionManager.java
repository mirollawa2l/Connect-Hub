
package friendManagment.Backend;
import userdatabasemanagement.User;
import userdatabasemanagement.UserDatabaseManagement;
import java.util.ArrayList;


public class FriendSuggestionManager {
    private FriendSuggestion suggestion;
    private ArrayList< FriendSuggestion >ListOfSuggestions=new ArrayList<>();
    private ManageFriends friendsManager;
    private UserDatabaseManagement accountManager ;
    private User thisUser;
    
   public FriendSuggestionManager(UserDatabaseManagement accountManager,ManageFriends friendsManager,User thisUser){
    this.accountManager=accountManager;
    this.friendsManager=friendsManager;
    this.thisUser=thisUser;
}
   
    public ArrayList<FriendSuggestion> generateSuggestions(){
        ArrayList <User> Suggested= new ArrayList<>();
   
        for (User user:accountManager.loadUsers()){
           for(User friend:friendsManager.getFriends()){
               if (user.equals(friend));
                   Suggested.remove(user);
           }}
        for(User user:Suggested){
            suggestion=new FriendSuggestion(user,thisUser);
            ListOfSuggestions.add(suggestion);}
        return ListOfSuggestions;
    }
    
    public FriendSuggestion sendSuggestion(User suggested){
        suggestion=new FriendSuggestion(suggested,thisUser);
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
