
package com.mycompany.friendmangement.Backend;
import userdatabasemanagement.User;

public class FriendRequest {

private User receiver ;
private User sender;
private String state ;
 
public FriendRequest(User receiver,User sender,String state){
    this.receiver=receiver;
    this.sender=sender;
    this.state=state;

}
    public String getState(){
        return state;}

    public void setState(String state) {
        this.state = state;
    }

    public User getSender() {
        return sender;
    }

    public User getReceiver() {
        return receiver;
    }
    
    
}

 