
package friendManagment.Backend;
import userdatabasemanagement.User;

  public class FriendSuggestion {
      
      private User suggested;
      private User asking;
      
      public FriendSuggestion(User suggested,User asking){
       this.suggested=suggested;
       
      }
      // singleton design pattern 
private FriendSuggestion(){}

    public User getSuggested() {
        return suggested;
    }

    public User getAsking() {
        return asking;
    }
      
      
  
    
}
