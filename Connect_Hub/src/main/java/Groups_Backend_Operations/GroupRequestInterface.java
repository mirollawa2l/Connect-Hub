/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Groups_Backend_Operations;

import Groups_Backend.Group;
import userdatabasemanagement.User;

/**
 *
 * @author sherrygirguis
 */
public interface GroupRequestInterface {
  
    public abstract Group getGroup();
    public abstract void setGroup(Group group);
    public abstract User getSender();
     public abstract void setSender(User sender);
    
}
