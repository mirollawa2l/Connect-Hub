
package com.mycompany.friendmangement.Backend;

import java.util.ArrayList;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;


public class ManageFriends {
    private ArrayList< User >friendList;
     private ArrayList< User >blockList;
 
    
   public ManageFriends(){
      friendList=new ArrayList<>();
      blockList=new ArrayList<>();
   }
   
   public void switchingUsers(){
   
   }
   
   
      public void AddFriend(User friend){
           if(!friendList.contains(friend))
              friendList.add(friend);}
      
      public void RemoveFriend (User friend){
          if(friendList.contains(friend))
              friendList.remove(friend);}
      
      public void BlockFriend (User user){
          if(friendList.contains(user))
              friendList.remove(user);
        blockList.add(user);
      }
      public ArrayList<User> getFriends(){
          return friendList;
      }
       public ArrayList<User> getBlocked(){
          return friendList;
      }
      /* public String toString(ArrayList<User> friends){
          String string=null;
          for(User friend:friends)
              string+=(friend+",");
        return string;
      }
     public void saveTofile(){
          ObjectMapper mapper = new ObjectMapper();
          Map<String, Object> data = Map.of(
          "userId",User.getId(),
          "Friends",getFriends().toString(),
          "Blocked Accounts",getBlocked().toString());
          
           try {
            mapper.writeValue(new File("FriendsManagement.json"), data);
            System.out.println("JSON file created successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }*/
            
    
}
