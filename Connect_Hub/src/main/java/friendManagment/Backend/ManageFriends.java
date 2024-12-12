package friendManagment.Backend;


import userdatabasemanagement.User;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;
import userdatabasemanagement.CurrentUser;
import userdatabasemanagement.UserDatabaseManagement;

public class ManageFriends {
     private ArrayList< User >friendList;
     private ArrayList< User >blockList;
     private UserDatabaseManagement accountManager;
     private User thisUser;
      
   public  ManageFriends(){
   friendList=new ArrayList<>();
   blockList=new ArrayList<>();
   accountManager=new UserDatabaseManagement();
   thisUser = CurrentUser.getInstance().getCurrentUser();
   loadFriends(thisUser.getId());
   
    if (thisUser != null) {
    System.out.println("User ID: " + thisUser.getId());
    } else {
    System.out.println("No user is currently logged in.");
    }
   }
       public void AddFriend(User friend){

       ArrayList<User> friends = loadFriends(thisUser.getId());
    if (friends != null) {
         if(friend.getId().equals(thisUser.getId())){
             JOptionPane.showMessageDialog(null,"Cannot add yourself found!","Error",JOptionPane.INFORMATION_MESSAGE);
         return ;}
          for(User user:loadFriends(thisUser.getId())){
              if(user.getId().equals(friend.getId())){
                   JOptionPane.showMessageDialog(null,"Friend already added found!","Error",JOptionPane.INFORMATION_MESSAGE);
                   return;}}
          for(User user:getBlocked())
              if(user.getId().equals(thisUser.getId())){
                  JOptionPane.showMessageDialog(null,"Blocked!","Error",JOptionPane.INFORMATION_MESSAGE);
                   return;}

           
           ObjectMapper objectMapper = new ObjectMapper();
           try {
             JsonNode rootNode = objectMapper.readTree(new File("users.json"));
             JsonNode thisUserNode = findUserById(rootNode, thisUser.getId());
            JsonNode friendNode = findUserById(rootNode, friend.getId());
            if (friendNode!= null && thisUserNode!= null) {
                ArrayNode thisUserFriends = ensureArrayNode(thisUserNode, "friends");
                       if (!thisUserFriends.has(friend.getId())||thisUserFriends==null) {
                    thisUserFriends.add(friend.getId());
                }
             ArrayNode friendFriends = ensureArrayNode(friendNode, "friends");
              if (!friendFriends.has(thisUser.getId())||friendFriends==null){
                    friendFriends.add(thisUser.getId());
                }
               objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("users.json"), rootNode);
                JOptionPane.showMessageDialog(null,"Friend added successfully!","Success",JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,"No user found!","Error",JOptionPane.INFORMATION_MESSAGE);
            }    
            }
            catch (IOException e) {
            e.printStackTrace();

        }}}
      public void RemoveFriend (User friend){
          
             // Check if friend is not the current user and is not already in the friend list
    if (friend != thisUser && friendList.contains(friend)) {
        friendList.remove(friend);
        System.out.println("Removed friend: " + friend.getUsername());}
        
                 ObjectMapper objectMapper = new ObjectMapper();
           try {
             JsonNode rootNode=objectMapper.readTree(new File("users.json"));
             JsonNode thisUserNode=findUserById(rootNode, thisUser.getId());
            JsonNode friendNode=findUserById(rootNode, friend.getId());
            if (friendNode!=null && thisUserNode!=null){
                ArrayNode thisUserFriends =ensureArrayNode(thisUserNode,"friends");
            for (int i=0;i<thisUserFriends.size();i++){
                if (thisUserFriends.get(i).asText().equals(friend.getId())) {
                    thisUserFriends.remove(i);
                    break;}}
            ArrayNode friendFriends=ensureArrayNode(friendNode,"friends");
           for (int i=0;i<friendFriends.size();i++){
                if (friendFriends.get(i).asText().equals(thisUser.getId())) {
                    friendFriends.remove(i);
                    break;}}
           objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("users.json"), rootNode);
          JOptionPane.showMessageDialog(null,"Friend removed successfully!","Success",JOptionPane.INFORMATION_MESSAGE);}
             else {
                JOptionPane.showMessageDialog(null,"No user found!","Error",JOptionPane.INFORMATION_MESSAGE);
            }    
         }catch(IOException e) {
            e.printStackTrace();
        }
           }
      
      private ArrayNode ensureArrayNode(JsonNode node,String fieldName) {
    JsonNode fieldNode = node.get(fieldName); // Retrieve the node for the given field (e.g., "friends")
    // If the field is missing or is not an array, create a new ArrayNode
    if (fieldNode == null || !fieldNode.isArray()) {
        ArrayNode newArray = new ObjectMapper().createArrayNode(); // Create a new empty ArrayNode
        ((ObjectNode)node).set(fieldName,newArray); // Set the new empty array as the value for the field
        return newArray;
    }
    // If the field is already an ArrayNode, return it
    return (ArrayNode) fieldNode;
}

      public void BlockFriend (User user){
         ArrayList<String> BlockedList=new ArrayList();
          ArrayList<User>friends=loadFriends(thisUser.getId());
          if(isFriend(user,friends))
              RemoveFriend(user);
            
        blockList.add(user);
        for(User block :getBlocked())
            BlockedList.add(block.getId());
        
            thisUser.setBlockList(BlockedList);
             accountManager.saveDatabase();

      }
      
      public void unBlockFriend (User user){
          if(blockList.contains(user)) 
              blockList.remove(user);
       accountManager.saveDatabase();
      
        
      }
      
    public boolean isFriend(User user, ArrayList<User> friends) {
    for (User friend : friends) {
        if (friend.getId().equals(user.getId())) {
            return true;
        }
    }
    return false;
}
      public ArrayList<User> getFriends(){
          return friendList;
      }
       public ArrayList<User> getBlocked(){
          return blockList;
      }
     
    public ArrayList<User>loadFriends(String userId){
    ObjectMapper objectMapper = new ObjectMapper();

        try {
             JsonNode rootNode = objectMapper.readTree(new File("users.json"));
             JsonNode userNode = findUserById(rootNode, userId);

        if (userNode != null && userNode.has("friends")) {
            JsonNode friendsNode = userNode.get("friends");


            if ( friendsNode.isArray()) {
                  for (JsonNode friendIdNode : friendsNode) {
                     String friendId = friendIdNode.asText();// Extract the friend's ID as a string
                    
                    // Skip the current user from their friend list
                     if (friendId.equals(userId)) {
                        System.out.println("Skipping self ID: " + userId);
                        continue;
                    }
                      // Check if the friend is already in the list before adding
                     boolean alreadyAdded = false;
                    for (User existingFriend : friendList) {
                        if (existingFriend.getId().equals(friendId)) {
                            alreadyAdded = true;
                            break;
                        }
                    }
                     // If the friend is not already in the list, load the full user object and add it to the list
                    if (!alreadyAdded) {
                    JsonNode friendNode = findUserById(rootNode, friendId); // Fetch full user object
                    if (friendNode != null) {
                        User user = objectMapper.treeToValue(friendNode, User.class);
                        friendList.add(user);
                    }
                }
                    
                }} 
            } else {
                 JOptionPane.showMessageDialog(null,"Cannot load friends!","Error",JOptionPane.INFORMATION_MESSAGE);
            }    }
            
           
         catch (IOException e) {
            e.printStackTrace();
        }
        for (User u : friendList) {
        System.out.println("Loaded user: " + u.getUsername());
    }
      return friendList;
    }
    public JsonNode findUserById(JsonNode rootNode,String userId){
       for (JsonNode userNode : rootNode){
            if(userNode.get("id").asText().equals(userId))
                return userNode;
            }
         return null;
}
 
// public ArrayList<User>loadBlocked(String userId){
//    ObjectMapper objectMapper = new ObjectMapper();
//
//        try {
//             JsonNode rootNode = objectMapper.readTree(new File("users.json"));
//             JsonNode userNode = findUserById(rootNode, userId);
//
//        if (userNode != null && userNode.has("blockList")) {
//            JsonNode blockedNode = userNode.get("blockList");
//
//
//            if ( blockedNode.isArray()) {
//                  for (JsonNode blockedIdNode : blockedNode) {
//                     String blockId = blockedIdNode.asText();// Extract the friend's ID as a string
//                    
//                    // Skip the current user from their friend list
//                     if (blockId.equals(userId)) {
//                        System.out.println("Skipping self ID: " + userId);
//                        continue;
//                    }
//                      // Check if the friend is already in the list before adding
//                     boolean alreadyBlocked = false;
//                    for (User existingFriend : blockList) {
//                        if (existingFriend.getId().equals(blockId)) {
//                            alreadyBlocked = true;
//                            break;
//                        }
//                    }
//                     // If the friend is not already in the list, load the full user object and add it to the list
//                    if (!alreadyBlocked) {
//                    JsonNode blockNode = findUserById(rootNode, blockId); // Fetch full user object
//                    if (blockedNode != null) {
//                        User user = objectMapper.treeToValue(blockNode, User.class);
//                        blockList.add(user);
//                    }
//                }
//                    
//                }} 
//            } else {
//                 JOptionPane.showMessageDialog(null,"Cannot load friends!","Error",JOptionPane.INFORMATION_MESSAGE);
//            }    }
//            
//           
//         catch (IOException e) {
//            e.printStackTrace();
//        }
//        for (User u : blockList) {
//        System.out.println("Loaded user: " + u.getUsername());
//    }
//      return blockList;
//    }

// public void saveBlockList(User friend){
//       ArrayList<User> blocked = loadBlocked(thisUser.getId());
//    if (blocked != null) {
//         if(friend.getId().equals(thisUser.getId())){
//             JOptionPane.showMessageDialog(null,"Cannot block yourself found!","Error",JOptionPane.INFORMATION_MESSAGE);
//         return ;}
//          for(User user:loadBlocked(thisUser.getId())){
//              if(user.getId().equals(friend.getId())){
//                   JOptionPane.showMessageDialog(null," already blocked!","Error",JOptionPane.INFORMATION_MESSAGE);
//                   return;}}
//          
//           
//           ObjectMapper objectMapper = new ObjectMapper();
//           try {
//             JsonNode rootNode = objectMapper.readTree(new File("users.json"));
//             JsonNode thisUserNode = findUserById(rootNode, thisUser.getId());
//   
//            if (thisUserNode!= null) {
//                ArrayNode thisUserBlockList = ensureArrayNode(thisUserNode, "blockList");
//                       if (!thisUserBlockList .has(friend.getId())||thisUserBlockList ==null) {
//                    thisUserBlockList .add(friend.getId());
//                }
//            
//               objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("users.json"), rootNode);
//                JOptionPane.showMessageDialog(null,"Blocked successfully!","Success",JOptionPane.INFORMATION_MESSAGE);
//            } else {
//                JOptionPane.showMessageDialog(null,"No user found!","Error",JOptionPane.INFORMATION_MESSAGE);
//            }    
//            }
//            catch (IOException e) {
//            e.printStackTrace();
//        }}}


    
}
 
    
 






