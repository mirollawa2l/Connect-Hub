/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Notifications;

import userdatabasemanagement.User;

/**
 *
 * @author mirol
 */
public interface NotificationsInt {

    public abstract String getMessage();

    public abstract void setMessage(String message);

    public abstract User getReciever();

    public abstract void setReciever(User reciever);

    public abstract User getSender();

    public abstract void setSender(User sender);

    public abstract String getType();

    public abstract void setType(String type);

    public abstract boolean isActionable();

    public abstract void setActionable(boolean actionable);

    @Override
    public abstract String toString();

}
