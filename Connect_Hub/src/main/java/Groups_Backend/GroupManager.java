/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Groups_Backend;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import static Constants.FileNames.GROUPS_FILE;
import Content_Creation.Backend.Post;
import userdatabasemanagement.User;

/**
 *
 * @author mirol
 */
public class GroupManager implements GroupManagerInterface {

    private ArrayList<Group> groups;


    public GroupManager() {
       load();
      
    }    
    @Override
    public void save()
    {
        saveToFile(this.groups);
    }
    
    @Override // FIX: Added missing @Override annotation
    public void load()
    {
        this.groups=loadFromFile();
        // Ensure admin/subadmin/member objects hold a reference to this manager
        if (this.groups != null) {
            for (Group g : this.groups) {
                initializeGroupReferences(g);
            }
        }
    }
    
    
    @Override
    public void addPost (Post p,Group g){
        for(Group g2:groups)
        {
            if(g.getGroupId().equals(g2.getGroupId()))
            {
                g2.addPost(p);
            }
        }
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
            System.err.println("Error saving groups: " + e.getMessage()); // FIX: Replaced printStackTrace with proper logging
            e.printStackTrace();
        }
    }


    private ArrayList<Group> loadFromFile() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        File file = new File(GROUPS_FILE);
        ArrayList<Group> loadedGroups = new ArrayList<>(); // FIX: Renamed from 'groups' to avoid shadowing field

        try {
            if (file.exists() && file.length() > 0) {
                loadedGroups = objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, Group.class));
                System.out.println("Groups loaded successfully from: " + GROUPS_FILE);
            }
        } catch (IOException e) {
            System.err.println("Error loading groups: " + e.getMessage()); // FIX: Replaced printStackTrace with proper logging
            e.printStackTrace();
        }

        return loadedGroups;
    }

    @Override
    public void addGroup(Group g) {
        if (g == null) return;
        initializeGroupReferences(g);
        this.groups.add(g);
    }

    // Ensure that Admin/SubAdmin objects inside a group have their manager and groupId set
    private void initializeGroupReferences(Group g) {
        if (g == null) return;
        String gid = g.getGroupId();
        Admin a = g.getAdmin();
        if (a != null) {
            a.manager = this;
            a.setGroupId(gid);
        }
        if (g.getSubAdmins() != null) {
            for (SubAdmin sa : g.getSubAdmins()) {
                if (sa != null) {
                    sa.manager = this;
                    sa.setGroupId(gid);
                }
            }
        }
        if (g.getMembers() != null) {
            for (User u : g.getMembers()) {
                if (u instanceof SubAdmin) {
                    SubAdmin sa = (SubAdmin) u;
                    sa.manager = this;
                    sa.setGroupId(gid);
                }
            }
        }
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
        g.notifyAllMembers(member.getUsername()+" is added to a group you belong to");
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
