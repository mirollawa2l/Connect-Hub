
package com.mycompany.friendmangement.Backend;
import userdatabasemanagement.User;

  public class FriendSuggestion {
      
      private User suggested;
      private User asking;
      
      public FriendSuggestion(User suggested,User asking){
       this.suggested=suggested;
       
      }

    public User getSuggested() {
        return suggested;
    }

    public User getAsking() {
        return asking;
    }
      
      
  
    
}
