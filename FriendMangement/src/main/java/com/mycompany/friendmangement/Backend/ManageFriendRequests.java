
package com.mycompany.friendmangement.Backend;

import java.util.ArrayList;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;


public class ManageFriendRequests {
    
   private FriendRequest friendRequest;
   private ManageFriends friendsManager;
   private ArrayList <FriendRequest> listOfRequests=new ArrayList<>() ;
   private UserAccountManagement accountManager ;
   
   public ManageFriendRequests(UserAccountManagement accountManager,ManageFriends friendsManager){
    this.accountManager=accountManager;
    this.friendsManager=friendsManager;
}
    public FriendRequest sendRequest(User receiver){
        friendRequest=new FriendRequest(receiver,"pending");
         listOfRequests.add(friendRequest);
        return friendRequest;
    }
    public void acceptRequest(FriendRequest friendRequest){
        friendsManager.AddFriend(friendRequest.getReceiver());
        //add friend to the other user 
        friendRequest.setState("accepted");
         listOfRequests.remove(friendRequest);
        //saveTofile();
    }
    public void declineRequest(FriendRequest friendRequest){
        listOfRequests.remove(friendRequest);
        friendRequest.setState("declined");
        //saveTofile();
        
    }  public FriendRequest getRequest(String username){
            for(FriendRequest request:listOfRequests)
                if(username.equals(request.getReceiver().getUsername()))
                    return request;
            return null;
       
       }
    public ArrayList<FriendRequest> getRequests(){
          return listOfRequests;
      }
    
 /* public String toString(ArrayList<FriendRequest> listOfRequests){
          String string=null;
          for(FriendRequest friendRequest:listOfRequests)
              string+=(friendRequest+",");
        return string; 
      } 
      public void saveTofile(){
          ObjectMapper mapper = new ObjectMapper();
          
          Map<String, Object> data = Map.of(
          "Requests",toString(listOfRequests));
           try {
            mapper.writeValue(new File(".json"), data);
            System.out.println("JSON file created successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
            }*/
    
}

