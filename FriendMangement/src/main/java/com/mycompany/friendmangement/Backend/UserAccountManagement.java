
package com.mycompany.friendmangement.Backend;

import java.util.ArrayList;

public class UserAccountManagement {
    private ArrayList<User> users=new ArrayList<>();
    public ArrayList<User> loadUsers(){
    User user=new User("12345","miroula");
    users.add(user);
         return  users;  }
}
