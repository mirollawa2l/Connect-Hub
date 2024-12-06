
package friendManagment.Backend;
import com.fasterxml.jackson.databind.JsonNode;
import userdatabasemanagement.UserDatabaseManagement;
import userdatabasemanagement.User;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.File;
import java.io.IOException;
import userdatabasemanagement.CurrentUser;


//ensure thisUser is the senser not the receiver

public class ManageFriendRequests {
    
   private FriendRequest friendRequest;
   private ManageFriends friendsManager=new ManageFriends();;
   private ArrayList <FriendRequest> listOfRequests=new ArrayList<>() ;
   private UserDatabaseManagement accountManager=new UserDatabaseManagement();
   private User thisUser= CurrentUser.getInstance().getCurrentUser();;
   

    public FriendRequest sendRequest(User receiver,User sender){
        friendRequest=new FriendRequest(receiver,sender,"pending");
         listOfRequests.add(friendRequest);
        return friendRequest;
    }
    public void acceptRequest(FriendRequest friendRequest){
        friendsManager.AddFriend(friendRequest.getReceiver());
        //add friend to the other user 
        friendRequest.setState("accepted");
         listOfRequests.remove(friendRequest);
        addToFile(friendRequest);
    }
    public void declineRequest(FriendRequest friendRequest){
        listOfRequests.remove(friendRequest);
        friendRequest.setState("declined");
        removeFromFile(friendRequest);
        
    }  public FriendRequest getRequest(String username){
            for(FriendRequest request:listOfRequests)
                if(username.equals(request.getReceiver().getUsername()))
                    return request;
            return null;
       }
    public ArrayList<FriendRequest> getRequests(){
          return listOfRequests;
      }
    public void addToFile(FriendRequest friendRequest){
        
              try {
              ObjectMapper objectMapper = new ObjectMapper();
             JsonNode rootNode = objectMapper.readTree(new File("users.json"));
              
             ensureFriendRequestField(rootNode);
             JsonNode senderNode = findUserById(rootNode, friendRequest.getSender().getId());
            JsonNode receiverNode = findUserById(rootNode, friendRequest.getReceiver().getId());

            if (receiverNode != null &&  senderNode!= null) {
                ArrayNode senderFriendRequests = (ArrayNode)senderNode.get("sentFriendRequests");
            if (!senderFriendRequests.has( friendRequest.getReceiver().getId())) {
                    senderFriendRequests.add( friendRequest.getReceiver().getId());
                }
             ArrayNode receiverFriendRequests = (ArrayNode) receiverNode .get("friendRequests");
              if (!receiverFriendRequests.has(friendRequest.getSender().getId())) {
                   receiverFriendRequests.add(friendRequest.getSender().getId());
                }
               objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("users.json"), rootNode);
                System.out.println("Request added successfully!");
            } else {
                System.out.println("Sender or recipient not found");
            }    
            }
            catch (IOException e) {
            e.printStackTrace();
        }}
    
    public JsonNode findUserById(JsonNode rootNode,String userId){
       for (JsonNode userNode : rootNode){
            if(userNode.get("userId").asText().equals(userId))
                return userNode;
            }
         return null;
    }
    
    public void removeFromFile(FriendRequest friendRequest){
        
              try {
              ObjectMapper objectMapper = new ObjectMapper();
             JsonNode rootNode = objectMapper.readTree(new File("users.json"));
              
             ensureFriendRequestField(rootNode);
             JsonNode senderNode = findUserById(rootNode, friendRequest.getSender().getId());
            JsonNode receiverNode = findUserById(rootNode, friendRequest.getReceiver().getId());

            if (receiverNode != null && senderNode!= null) {
                ArrayNode senderFriendRequests = (ArrayNode)senderNode.get("sentFriendRequests");
           for (int i = 0; i < senderFriendRequests.size(); i++) {
                if (senderFriendRequests.get(i).asText().equals(friendRequest.getReceiver().getId())) {
                   senderFriendRequests.remove(i);
                    break; 
                }
            }
                
             ArrayNode receiverFriendRequests = (ArrayNode) receiverNode.get("friendRequests");
             for (int i = 0; i < receiverFriendRequests.size(); i++) {
                if (receiverFriendRequests.get(i).asText().equals(friendRequest.getSender().getId())) {
                   receiverFriendRequests.remove(i);
                    break; 
                }
            }
               objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("users.json"), rootNode);
                System.out.println("Request removed successfully!");
            } else {
                System.out.println("Sender or recipient not found");
            }    
            }
            catch (IOException e) {
            e.printStackTrace();
        }}
    public User getThisUser(){
    return thisUser;
    }
    public void ensureFriendRequestField(JsonNode rootNode){
   
    for (JsonNode userNode : rootNode) {
        ObjectNode userObject = (ObjectNode) userNode;
        if (!userObject.has("friendRequests")) {
            userObject.putArray("friendRequests");
        }
        if (!userObject.has("sentFriendRequests")) {
            userObject.putArray("sentFriendRequests");
        }
    }
}
    
    }
    
    
 


