/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Notifications;

/**
 *
 * @author HP
 */
import userdatabasemanagement.User;

public class Notification {
    private String message;
    private User reciever;
    private User sender;
    private String type; // friend request , acception , new post from group,new comment , new like 
    private boolean actionable ; // if the notification requires an action

    public Notification(String message, User reciever, User sender, String type, boolean actionable) {
        this.message = message;
        this.reciever = reciever;
        this.sender = sender;
        this.type = type;
        this.actionable = actionable;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getReciever() {
        return reciever;
    }

    public void setReciever(User reciever) {
        this.reciever = reciever;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isActionable() {
        return actionable;
    }

    public void setActionable(boolean actionable) {
        this.actionable = actionable;
    }

    @Override
    public String toString() {
        return "Notification{" + "message=" + message + '}';
    }
    
    

   
   
    
}