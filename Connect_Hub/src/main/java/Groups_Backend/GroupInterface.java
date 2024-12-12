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
public interface GroupInterface {

    public abstract ArrayList<User> getRequestedMembers();

    public abstract void setRequestedMembers(ArrayList<User> requestedMembers);

    public abstract void setPosts(ArrayList<Post> posts);

    public abstract ArrayList<Post> getPosts();
    
    public abstract void addPost(Post p);

    public abstract String getGroupId();

    public abstract Admin getAdmin();

    public abstract void setAdmin(Admin admin);

    public abstract ArrayList<SubAdmin> getSubAdmins();

    public abstract void setSubAdmins(ArrayList<SubAdmin> subAdmins);

    public abstract ArrayList<User> getMembers();

    public abstract void setMembers(ArrayList<User> members);

    public abstract String getName();

    public abstract void setName(String name);

    public abstract String getDescription();

    public abstract void setDescription(String description);

    public abstract String getGroupPhotoPath();

    public abstract void setGroupPhotoPath(String groupPhotoPath);

}
