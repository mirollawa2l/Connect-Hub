
package com.mycompany.friendmangement.Backend;


public class FriendRequest {

private User receiver ;
private String state ;
 
public FriendRequest(User receiver,String state){
    this.receiver=receiver;
    this.state=state;

}
    public String getState(){
        return state;}

    public void setState(String state) {
        this.state = state;
    }

    public User getReceiver() {
        return receiver;
    }
    
    
}

 