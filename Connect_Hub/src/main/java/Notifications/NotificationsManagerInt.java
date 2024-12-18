/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Notifications;

import java.util.ArrayList;
import userdatabasemanagement.User;

/**
 *
 * @author mirol
 */
public interface NotificationsManagerInt {

    public abstract ArrayList<Notification> loadNotifications();

    public abstract void addNotification(String message, User reciever, User sender, String type, boolean actionable);

    public abstract ArrayList<Notification> getNotificationsForUser(User user);

    public abstract void removeNotification(Notification n);

    public abstract void clearNotificationForUser(User user);

}
