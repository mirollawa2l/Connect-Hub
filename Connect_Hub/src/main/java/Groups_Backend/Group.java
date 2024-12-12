/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Groups_Backend;

import Content_Creation.Backend.Post;
import static java.lang.String.valueOf;
import java.util.ArrayList;
import userdatabasemanagement.User;

/**
 *
 * @author mirol
 */
public class Group implements GroupInterface {

    private String groupId;
    private Admin admin;
    private ArrayList<SubAdmin> subAdmins;
    private ArrayList<User> members;
    private ArrayList<User> requestedMembers;
    private ArrayList<Post> posts;
    private String name;
    private String description;
    private String groupPhotoPath;

    
    private static int GroupCount = 0;

    public Group() {
        this.groupId="G"+valueOf(++GroupCount);
    }
    
    
    @Override
    public ArrayList<User> getRequestedMembers() {
        return requestedMembers;
    }

    @Override
    public void setRequestedMembers(ArrayList<User> requestedMembers) {
        this.requestedMembers = requestedMembers;
    }

    @Override
    public ArrayList<Post> getPosts() {
        return posts;
    }

    @Override
    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }
    
    @Override
    public void addPost(Post p)
    {
        this.posts.add(p);
    }

    @Override
    public String getGroupId() {
        return groupId;
    }

    @Override
    public Admin getAdmin() {
        return admin;
    }

    @Override
    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    @Override
    public ArrayList<SubAdmin> getSubAdmins() {
        return subAdmins;
    }

    @Override
    public void setSubAdmins(ArrayList<SubAdmin> subAdmins) {
        this.subAdmins = subAdmins;
    }

    @Override
    public ArrayList<User> getMembers() {
        return this.members;
    }

    @Override
    public void setMembers(ArrayList<User> members) {
        this.members = members;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getGroupPhotoPath() {
        return groupPhotoPath;
    }

    @Override
    public void setGroupPhotoPath(String groupPhotoPath) {
        this.groupPhotoPath = groupPhotoPath;
    }

}
