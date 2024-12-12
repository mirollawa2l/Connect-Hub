/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Groups_Backend;

import Content_Creation.Backend.Post;
import java.util.ArrayList;
import userdatabasemanagement.User;

/**
 *
 * @author mirol
 */
public interface GroupManagerInterface {

    public abstract void addPost(Post p,Group g);

    public abstract ArrayList<Group> getGroups();

    public abstract void setGroups(ArrayList<Group> groups);

    public abstract void saveToFile(ArrayList<Group> groups);

    public abstract ArrayList<Group> loadFromFile();

    public abstract void addGroup(Group g);

    public abstract void deleteGroup(Group g);

    public abstract Group getGroup(String id);

    public abstract boolean isSAdmin(User user, Group g);

    public abstract boolean isSubAdmin(User user, Group g);

    public abstract boolean isMember(User user, Group g);

    public abstract User getMember(String id, Group g);
}
