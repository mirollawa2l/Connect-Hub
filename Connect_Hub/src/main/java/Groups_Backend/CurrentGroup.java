/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Groups_Backend;


/**
 *
 * @author sherrygirguis
 */
public class CurrentGroup {
    private static CurrentGroup instance;
    private static Group currentGroup;
         private CurrentGroup(){
    
}
 public static synchronized CurrentGroup getInstance() {
        if (instance == null) {
            instance = new CurrentGroup();
        }
        return instance;
    }

    public synchronized Group getCurrentGroup() {
        return currentGroup;
    }

    public synchronized void setCurrentGroup(Group currentGroup) {
        this.currentGroup = currentGroup;
    }
 
 
}

