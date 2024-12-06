
package friendManagment.Backend;

import userdatabasemanagement.User;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
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
   User thisUser = CurrentUser.getInstance().getCurrentUser();
   loadFriends(thisUser.getId());

   }
      
      public void AddFriend(User friend){
           if(!friendList.contains(friend)||friendList==null)
              friendList.add(friend);
           
           ObjectMapper objectMapper = new ObjectMapper();
           try {
             JsonNode rootNode = objectMapper.readTree(new File("users.json"));
             JsonNode thisUserNode = findUserById(rootNode, thisUser.getId());
            JsonNode friendNode = findUserById(rootNode, friend.getId());
            
            if (friendNode != null && thisUserNode!= null) {
                ArrayNode thisUserFriends = (ArrayNode)thisUserNode.get("friends");
            if (!thisUserFriends.has(friend.getId())) {
                    thisUserFriends.add(friend.getId());
                }
              
             ArrayNode friendFriends = (ArrayNode) friendNode.get("friends");
              if (!friendFriends.has(thisUser.getId())) {
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
        }}
      
      public void RemoveFriend (User friend){
          
          if(friendList.contains(friend)||friendList==null)
              friendList.remove(friend);
                 ObjectMapper objectMapper = new ObjectMapper();
           try {
             JsonNode rootNode = objectMapper.readTree(new File("users.json"));
             JsonNode thisUserNode = findUserById(rootNode, thisUser.getId());
            JsonNode friendNode = findUserById(rootNode, friend.getId());
            if (friendNode != null && thisUserNode!= null) {
                ArrayNode thisUserFriends = (ArrayNode)thisUserNode.get("friends");
            for (int i = 0; i < thisUserFriends.size(); i++) {
                if (thisUserFriends.get(i).asText().equals(friend.getId())) {
                    thisUserFriends.remove(i);
                    break;}}
            ArrayNode friendFriends = (ArrayNode) friendNode.get("friends");
           for (int i = 0; i < friendFriends.size(); i++) {
                if (friendFriends.get(i).asText().equals(friend.getId())) {
                    friendFriends.remove(i);
                    break;}}
           objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("users.json"), rootNode);
          JOptionPane.showMessageDialog(null,"Friend removed successfully!","Success",JOptionPane.INFORMATION_MESSAGE);}
             else {
                JOptionPane.showMessageDialog(null,"No user found!","Error",JOptionPane.INFORMATION_MESSAGE);
            }    
         } catch (IOException e) {
            e.printStackTrace();
        }
           }

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
     
    public ArrayList<User>loadFriends(String userId){
    ObjectMapper objectMapper = new ObjectMapper();

        try {
             JsonNode rootNode = objectMapper.readTree(new File("users.json"));
             for (JsonNode userNode : rootNode) {
            if (userNode.has("userId") && userNode.get("userId").asText().equals(userId)) {
             JsonNode friendsNode = rootNode.get("friends");

            if (friendsNode != null && friendsNode.isArray()) {
                  for (JsonNode friendNode : friendsNode) {
                    User user = objectMapper.treeToValue(friendNode, User.class);
                    friendList.add(user);
                    System.out.print(user);
                }
            } else {
                 JOptionPane.showMessageDialog(null,"Cannot load friends!","Error",JOptionPane.INFORMATION_MESSAGE);
            }    }
            }}
           
         catch (IOException e) {
            e.printStackTrace();
        }
      return friendList;
    }
    public JsonNode findUserById(JsonNode rootNode,String userId){
       for (JsonNode userNode : rootNode){
            if(userNode.get("userId").asText().equals(userId))
                return userNode;
            }
         return null;
    }
            

}
