
package com.mycompany.friendmangement.Backend;


public class User {
    private String Id;
    private String username;
    
    public User(String Id,String name){
    this.Id=Id;
    this.username=name;
    }
     public String getId(){
     return Id;
     }

    public String getUsername() {
        return username;
    }
        
}
