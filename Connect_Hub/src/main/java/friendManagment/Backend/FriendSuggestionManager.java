
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
    private User thisUser;
    
   public FriendSuggestionManager(){
      ListOfSuggestions=new ArrayList<>(); 
      friendsManager=new ManageFriends();
      accountManager =new UserDatabaseManagement();
      thisUser= CurrentUser.getInstance().getCurrentUser();
      generateSuggestions();
}
   
    public ArrayList<FriendSuggestion> generateSuggestions(){
        ArrayList <User> Suggested= new ArrayList<>();
         ListOfSuggestions.clear();
   if(friendsManager.getFriends()!=null){
        for (User user:accountManager.loadUsers()){
            Suggested.add(user);
           for(User friend:friendsManager.getFriends()){
               if (user.equals(friend))
                   Suggested.remove(user);
           }}}
   else { for (User user:accountManager.loadUsers()){
        Suggested.add(user);
   }
   }
   
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
      friendsManager.AddFriend(suggestion.getSuggested());
        ListOfSuggestions.remove(suggestion);
         JOptionPane.showMessageDialog(null,"Friend Request accepted successfully!","Success",JOptionPane.INFORMATION_MESSAGE);

    }
       public void declineFriendSuggestion(FriendSuggestion suggestion){
        ListOfSuggestions.remove(suggestion);
        JOptionPane.showMessageDialog(null,"Friend Request declined successfully!","Success",JOptionPane.INFORMATION_MESSAGE);
        
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
