/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Groups_Backend;

import java.util.ArrayList;
import userdatabasemanagement.User;

/**
 *
 * @author mirol
 */
public interface GroupInterface {

    public abstract Admin getAdmin();

    public abstract void setAdmin(Admin admin);

    public abstract ArrayList<SubAdmin> getSubAdmins();

    public abstract void setSubAdmins(ArrayList<SubAdmin> subAdmins);

    public abstract ArrayList<User> getMembers();

    public abstract void setMembers(ArrayList<User> members);

    public abstract ArrayList<User> getAllMembers();

    public abstract void setAllMembers(ArrayList<User> allMembers);

    public abstract String getName();

    public abstract void setName(String name);

    public abstract String getDescription();

    public abstract void setDescription(String description);

    public abstract String getGroupPhotoPath();

    public abstract void setGroupPhotoPath(String groupPhotoPath);

}
