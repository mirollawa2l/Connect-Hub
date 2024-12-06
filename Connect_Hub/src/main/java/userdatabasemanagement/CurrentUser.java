
package userdatabasemanagement;

/**
 *
 * @author sherrygirguis
 */
public class CurrentUser {
     private static CurrentUser instance;
    private User currentUser;


private CurrentUser(){
}
 public static synchronized CurrentUser getInstance() {
        if (instance == null) {
            instance = new CurrentUser();
        }
        return instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
 
 
}