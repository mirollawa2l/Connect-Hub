
package friendManagment.Backend;
import userdatabasemanagement.User;
import userdatabasemanagement.UserDatabaseManagement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import userdatabasemanagement.CurrentUser;


public class FriendSuggestionManager {
    private FriendSuggestion suggestion;
    private ArrayList< FriendSuggestion >ListOfSuggestions;
    private ManageFriends friendsManager;
    private UserDatabaseManagement accountManager;
    private ManageFriendRequests requestManager;
    private User thisUser;
    
    
   public FriendSuggestionManager(){
      ListOfSuggestions=new ArrayList<>(); 
      friendsManager=new ManageFriends();
      requestManager =new ManageFriendRequests();
      accountManager =new UserDatabaseManagement();
      thisUser= CurrentUser.getInstance().getCurrentUser();
      generateSuggestions();
}
   
    public ArrayList<FriendSuggestion> generateSuggestions(){
        ArrayList <User> Suggested= new ArrayList<>();
         ListOfSuggestions.clear();
    ArrayList<User> friends = friendsManager.loadFriends(thisUser.getId());
    ArrayList<FriendRequest> requests = requestManager.loadFriendRequests(thisUser.getId());
    if (friends != null) {
        // Add all users as potential suggestions
        for (User user:accountManager.loadUsers()){
             if (!user.getId().equals(thisUser.getId()) && !friendsManager.isFriend(user,friends)&& !requestManager.isRequest(user,requests)&&!friendsManager.getBlocked().contains(user)) {
                Suggested.add(user);

            }}}

        for(User user:Suggested){
            suggestion=new FriendSuggestion(user,thisUser);
            ListOfSuggestions.add(suggestion);}
        return ListOfSuggestions;
    }

     
    
    public FriendSuggestion sendSuggestion(User suggested){
           for (FriendSuggestion s : ListOfSuggestions) {
        if (s.getSuggested().equals(suggested)) {
            JOptionPane.showMessageDialog(null, "Suggestion already sent!", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
        
        suggestion=new FriendSuggestion(suggested,thisUser);
        ListOfSuggestions.add(suggestion);
         JOptionPane.showMessageDialog(null,"Friend Request ent successfully!","Success",JOptionPane.INFORMATION_MESSAGE);
        return suggestion;}
        
    public void acceptFriendSuggestion(FriendSuggestion suggestion){
      requestManager.sendRequest(thisUser,suggestion.getSuggested());
        ListOfSuggestions.remove(suggestion);

    }
       public void declineFriendSuggestion(FriendSuggestion suggestion){
        ListOfSuggestions.remove(suggestion);
        JOptionPane.showMessageDialog(null,"Friend Suggestiom declined successfully!","Success",JOptionPane.INFORMATION_MESSAGE);
        
    } 
       public ArrayList< FriendSuggestion >getSuggestions(){
       return ListOfSuggestions;
       }
       
        public FriendSuggestion getSuggestion(String username){
            for(FriendSuggestion suggestion:ListOfSuggestions)
                if(username.equals(suggestion.getSuggested().getUsername()))
                    return suggestion;
            return null;
       
       }
       
      
        

}
