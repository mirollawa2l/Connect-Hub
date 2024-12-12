
package friendManagment.Backend;
import com.fasterxml.jackson.databind.JsonNode;
import userdatabasemanagement.UserDatabaseManagement;
import userdatabasemanagement.User;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import friendManagment.Backend.FriendRequest;
import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;
import userdatabasemanagement.CurrentUser;


//ensure thisUser is the senser not the receiver

public class ManageFriendRequests {
    
   private FriendRequest friendRequest;
   private ManageFriends friendsManager=new ManageFriends();
   private ArrayList <FriendRequest> listOfRequests=new ArrayList<>() ;
   private UserDatabaseManagement accountManager=new UserDatabaseManagement();
   private User thisUser= CurrentUser.getInstance().getCurrentUser();
      

    public FriendRequest sendRequest(User sender,User receiver){
        boolean isFriend=false;
        boolean isBlocked=false;
        if(receiver.getId().equals(thisUser.getId())){
        JOptionPane.showMessageDialog(null,"Cannot add yourself!","Error",JOptionPane.INFORMATION_MESSAGE);
          return null;
        }
            
       
      for(User user:friendsManager.loadFriends(thisUser.getId())){
          
              if(user.getId().equals(receiver.getId())){
                  isFriend=true;
                   JOptionPane.showMessageDialog(null,"Friend already added !","Error",JOptionPane.INFORMATION_MESSAGE);
                   return null;
              }
      }
   
      for(User user:friendsManager.getBlocked()){
          
              if(user.getId().equals(receiver.getId())||user.getId().equals(sender.getId())){
                  isBlocked=true;
                   JOptionPane.showMessageDialog(null,"Blocked!","Error",JOptionPane.INFORMATION_MESSAGE);
                   return null;
              }
      }
         
        friendRequest=new FriendRequest(receiver,sender,"pending");
        
         listOfRequests.add(friendRequest);
      
         addToFile(friendRequest);
  
         JOptionPane.showMessageDialog(null,"friend Request added sucessfully","Sucess",JOptionPane.INFORMATION_MESSAGE);
          return friendRequest;
                         
    }
    
  
    public void acceptRequest(FriendRequest friendRequest){
      friendsManager.AddFriend(friendRequest.getSender());
       listOfRequests.remove(friendRequest);
        removeFromFile(friendRequest);
        
        
        JOptionPane.showMessageDialog(null,"friend Request accepted sucessfully","Sucess",JOptionPane.INFORMATION_MESSAGE);
    }
    public void declineRequest(FriendRequest friendRequest){
         listOfRequests.remove(friendRequest);
        removeFromFile(friendRequest);
       
        
         JOptionPane.showMessageDialog(null,"friend Request declined sucessfully","Sucess",JOptionPane.INFORMATION_MESSAGE);
    }  public FriendRequest getRequest(String username){
            for(FriendRequest request:listOfRequests)
                if(username.equals(request.getSender
        ().getUsername()))
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
            
             JsonNode senderNode = findUserById(rootNode, friendRequest.getSender().getId());
            JsonNode receiverNode = findUserById(rootNode, friendRequest.getReceiver().getId());
            
System.out.println("Sender ID: " + friendRequest.getSender().getId());
            System.out.println("Receiver ID: " + friendRequest.getReceiver().getId());
            
            if (receiverNode != null &&  senderNode!= null) {
                ArrayNode senderFriendRequests = ensureArrayNode(senderNode, "sentFriendRequests");
            boolean senderHasRequest = false;
            for (JsonNode node : senderFriendRequests) {
                if (node.asText().equals(friendRequest.getReceiver().getId())) {
                    senderHasRequest = true;
                    break;
                }
            }
            if (!senderHasRequest) {
                senderFriendRequests.add(friendRequest.getReceiver().getId());
            }

            // Ensure `friendRequests` exists as an ArrayNode
            ArrayNode receiverFriendRequests = ensureArrayNode(receiverNode, "friendRequests");
            boolean receiverHasRequest = false;
            for (JsonNode node : receiverFriendRequests) {
                if (node.asText().equals(friendRequest.getSender().getId())) {
                    receiverHasRequest = true;
                    break;
                }
            }
            if (!receiverHasRequest) {
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
            if(userNode.get("id").asText().equals(userId))
                return userNode;
            }
         return null;
    }
    
    public boolean isRequest(User user, ArrayList<FriendRequest> requests) {
    for (FriendRequest request : requests) {
        if (request.getReceiver().equals(user.getId())) {
            return true;
        }
    }
    return false;
}
    
    public void removeFromFile(FriendRequest friendRequest){
        
              try {
              ObjectMapper objectMapper = new ObjectMapper();
             JsonNode rootNode = objectMapper.readTree(new File("users.json"));
              
            
             JsonNode senderNode = findUserById(rootNode, friendRequest.getSender().getId());
            JsonNode receiverNode = findUserById(rootNode, friendRequest.getReceiver().getId());
            System.out.println("Sender ID: " + friendRequest.getSender().getId());
            System.out.println("Receiver ID: " + friendRequest.getReceiver().getId());
            
            if (receiverNode != null && senderNode!= null) {
                     ArrayNode senderFriendRequests = ensureArrayNode(senderNode, "sentFriendRequests");
                     if (senderFriendRequests == null || senderFriendRequests.size() == 0) {
                System.out.println("Sender's sent friend requests array is empty or null");
            } else {
                System.out.println("Sender's sent friend requests array is populated");
            }

           for (int i=0;i<senderFriendRequests.size();i++) {
               System.out.print(i);
                if (senderFriendRequests.get(i).asText().equals(friendRequest.getReceiver().getId())) {
                    senderFriendRequests.remove(i);
                    
                    break; 
                }
            }
             ArrayNode receiverFriendRequests = ensureArrayNode(receiverNode, "friendRequests");
                         if (receiverFriendRequests == null || receiverFriendRequests.size() == 0) {
                System.out.println("Receiver's received friend requests array is empty or null");
            } else {
                System.out.println("Receiver's received friend requests array is populated");
            }
             for (int i =0;i<receiverFriendRequests.size();i++) {
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
 
    private ArrayNode ensureArrayNode(JsonNode node, String fieldName) {
    JsonNode fieldNode = node.get(fieldName);
    if (fieldNode == null || !fieldNode.isArray()) {
       
        ((ObjectNode) node).putArray(fieldName);
        return (ArrayNode) node.get(fieldName);
    }
    return (ArrayNode) fieldNode;}
    
    public ArrayList<FriendRequest>loadFriendRequests(String userId){
    ObjectMapper objectMapper = new ObjectMapper();
     listOfRequests.clear();

        try {
             JsonNode rootNode = objectMapper.readTree(new File("users.json"));
                  JsonNode userNode = findUserById(rootNode, userId);
                  if (userNode != null) {
            // Load received friend requests
            JsonNode friendRequestsNode = userNode.get("friendRequests");
             if (friendRequestsNode != null && friendRequestsNode.isArray()) {
                for (JsonNode friendIdNode :friendRequestsNode) {
                    String friendId = friendIdNode.asText();
                    JsonNode friendNode = findUserById(rootNode, friendId);
                    if (friendNode != null) {
                           User friend = objectMapper.treeToValue(friendNode, User.class);
                           FriendRequest friendRequest=new FriendRequest(thisUser,friend,"pending");
                        listOfRequests.add(friendRequest);
                    }}}
//             JsonNode sentFriendRequestsNode = userNode.get("sentFriendRequests");
//            if (sentFriendRequestsNode != null && sentFriendRequestsNode.isArray()) {
//                for (JsonNode friendIdNode : sentFriendRequestsNode) {
//                    String friendId = friendIdNode.asText();
//                    JsonNode friendNode = findUserById(rootNode, friendId);
//
//                    if (friendNode != null) {
//                        User receiver = objectMapper.treeToValue(friendNode, User.class);
//                        FriendRequest friendRequest = new FriendRequest(receiver, thisUser, "pending");
//                        listOfRequests.add(friendRequest);
//                    }
//                }
//            }
//                
                System.out.println("Requests loaded successfully!");
 
            } else {
                System.out.println("Sender or recipient not found.");
            }
        }catch (IOException e) {
            e.printStackTrace();
      
        }
    return listOfRequests;
        
    }


}
 


