/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Groups_Backend;

import static Constants.FileNames.GROUPS_FILE;
import Content_Creation.Backend.Post;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import userdatabasemanagement.User;

/**
 *
 * @author mirol
 */
public class GroupManager implements GroupManagerInterface {

    private ArrayList<Group> groups;


    public GroupManager() {
        groups=new ArrayList<>();
       load();
      
    }    
    @Override
    public void save()
    {
        saveToFile(this.groups);
    }
    
    public void load()
    {
        this.groups=loadFromFile();
    }
    
    
    @Override
    public void addPost (Post p,Group g){
        g.addPost(p);
        save();
        load();
                } 
    @Override
    public ArrayList<Group> getGroups() {
        return groups;
    }

    @Override
    public void setGroups(ArrayList<Group> groups) {
        this.groups = groups;
    }

    private void saveToFile(ArrayList<Group> groups) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // For pretty printing JSON

        File file = new File(GROUPS_FILE);

        try {
            // Ensure the file exists or create a new one
            if (!file.exists()) {
                file.createNewFile();
            }

            // Overwrite the file with the new list of groups
            objectMapper.writeValue(file, groups);
            System.out.println("Groups successfully saved to: " + GROUPS_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private ArrayList<Group> loadFromFile() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        File file = new File(GROUPS_FILE);
        ArrayList<Group> groups = new ArrayList<>();

        try {
            if (file.exists() && file.length() > 0) {
                groups = objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, Group.class));
                System.out.println("Groups loaded successfully from: " + GROUPS_FILE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return groups;
    }

    @Override
    public void addGroup(Group g) {
        this.groups.add(g);
    }

    @Override
    public void deleteGroup(Group g) {
        this.groups.remove(g);
    }

    @Override
    public Group getGroup(String id) {
        for (Group g : groups) {
            if (g.getGroupId().equals(id)) {
                return g;
            }
        }
        return null;
    }
     public Group getGroupByName(String name) {
        for (Group g : groups) {
            if (g.getName().equals(name)) {
                return g;
            }
        }
        return null;
    }

    @Override
    public boolean isSubAdmin(User user, Group g) {
        for (SubAdmin admin : g.getSubAdmins()) {
            if (user.getId().equals(admin.getId())) {
                return true;
            }
        }
        System.out.println("User isn't a subAdmin for group: "+g.getName());
        return false;
    }

    @Override
    public boolean isMember(User user, Group g) {
      return g.isMember(user);
    }

    public void addMember(Member member,Group g)
    {
        g.getMembers().add(member);
        save();
        load();
    }
    
    @Override
    public User getMember(String id,Group g)
    {
       for(User user :g.getMembers())
       {
           if(id.equals(user.getId()))
               return user;
       }
        System.out.println("User isn't a member of the group");
        return null;
    }

    @Override
    public boolean isSAdmin(User user, Group g) {
      return user.getId().equals(g.getAdmin().getId());
    }


}
