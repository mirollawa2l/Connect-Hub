/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Groups_Backend;

import java.util.ArrayList;
import userdatabasemanagement.User;

/**
 *
 * @author mirol
 */
public class Group implements GroupInterface{
    private  Admin admin;
    private ArrayList<SubAdmin> subAdmins;
    private ArrayList<User> members;
    private ArrayList<User> allMembers;  
    private String name;
    private String description;
    private String groupPhotoPath;
    
   
    
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
        return members;
    }

    @Override
    public void setMembers(ArrayList<User> members) {
        this.members = members;
    }

    @Override
    public ArrayList<User> getAllMembers() {
        return allMembers;
    }

    @Override
    public void setAllMembers(ArrayList<User> allMembers) {
        this.allMembers = allMembers;
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
