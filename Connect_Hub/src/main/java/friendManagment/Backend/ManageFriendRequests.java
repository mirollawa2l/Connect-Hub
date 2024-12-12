
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
import javax.swing.JOptionPane;
import userdatabasemanagement.CurrentUser;


//ensure thisUser is the senser not the receiver

public class ManageFriendRequests {
    
   private FriendRequest friendRequest;
   private ManageFriends friendsManager=new ManageFriends();
   private ArrayList <FriendRequest> listOfRequests=new ArrayList<>() ;
   private UserDatabaseManagement accountManager=new UserDatabaseManagement();
   private User thisUser= CurrentUser.getInstance().getCurrentUser();
      

    public FriendRequest sendRequest(User receiver,User sender){
        boolean isFriend=false;
        for(User friend:friendsManager.loadFriends(thisUser.getId()))
            if(receiver==friend){
                isFriend=true;
            break;}
            else{
        friendRequest=new FriendRequest(receiver,sender,"pending");
         listOfRequests.add(friendRequest);
         addToFile(friendRequest);
       
         JOptionPane.showMessageDialog(null,"friend Request added sucessfully","Sucess",JOptionPane.INFORMATION_MESSAGE);
          return friendRequest;
            }
      
        if(isFriend)
             JOptionPane.showMessageDialog(null,"Already is a friend!","Error",JOptionPane.INFORMATION_MESSAGE);
          return null;
    }
    
  
    public void acceptRequest(FriendRequest friendRequest){
      friendsManager.AddFriend(friendRequest.getReceiver());
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
              
            
             JsonNode senderNode = findUserById(rootNode, friendRequest.getSender().getId());
            JsonNode receiverNode = findUserById(rootNode, friendRequest.getReceiver().getId());

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
    
    public void removeFromFile(FriendRequest friendRequest){
        
              try {
              ObjectMapper objectMapper = new ObjectMapper();
             JsonNode rootNode = objectMapper.readTree(new File("users.json"));
              
            
             JsonNode senderNode = findUserById(rootNode, friendRequest.getSender().getId());
            JsonNode receiverNode = findUserById(rootNode, friendRequest.getReceiver().getId());

            if (receiverNode != null && senderNode!= null) {
                     ArrayNode senderFriendRequests = ensureArrayNode(senderNode, "sentFriendRequests");
           for (int i=0;i< senderFriendRequests.size();i++) {
                if (senderFriendRequests.get(i).asText().equals(friendRequest.getReceiver().getId())) {
                    senderFriendRequests.remove(i);
                    break; 
                }
            }
             ArrayNode receiverFriendRequests = ensureArrayNode(receiverNode, "friendRequests");;
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
        ArrayNode newArray = new ObjectMapper().createArrayNode();
        ((ObjectNode) node).set(fieldName, newArray);
        return newArray;
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
                           FriendRequest friendRequest=new FriendRequest(friend,thisUser,"pending");
                        listOfRequests.add(friendRequest);
                    }}}
             JsonNode sentFriendRequestsNode = userNode.get("sentFriendRequests");
            if (sentFriendRequestsNode != null && sentFriendRequestsNode.isArray()) {
                for (JsonNode friendIdNode : sentFriendRequestsNode) {
                    String friendId = friendIdNode.asText();
                    JsonNode friendNode = findUserById(rootNode, friendId);

                    if (friendNode != null) {
                        User receiver = objectMapper.treeToValue(friendNode, User.class);
                        FriendRequest friendRequest = new FriendRequest(thisUser, receiver, "pending");
                        listOfRequests.add(friendRequest);
                    }
                }
            }
                
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
 


