
package userdatabasemanagement;


public class CurrentUser {
     private static CurrentUser instance;
    private static User currentUser;


private CurrentUser(){
    
}
 public static synchronized CurrentUser getInstance() {
        if (instance == null) {
            instance = new CurrentUser();
        }
        return instance;
    }

    public synchronized User getCurrentUser() {
        return currentUser;
    }

    public synchronized void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
 
 
}