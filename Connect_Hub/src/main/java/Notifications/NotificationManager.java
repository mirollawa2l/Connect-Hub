/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Notifications;

/**
 *
 * @author HP
 */
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import userdatabasemanagement.User;

public class NotificationManager implements NotificationsManagerInt{
    private static final String NOTIFICATION_FILE = "notifications.json";
    private final ObjectMapper objectMapper;
    private static NotificationManager instance;
    private ArrayList<Notification> notifications = new ArrayList<>();
// singleton pattern
    private NotificationManager() {
         objectMapper=new ObjectMapper();
         objectMapper.registerModule(new JavaTimeModule());
        notifications=loadNotifications();
       
    }
    public static NotificationManager getInstance(){
        if (instance == null)
            instance = new NotificationManager();
        return instance;
    }

  /* public NotificationManager() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        loadNotifications();
    }*/

    // Load notifications from the JSON file
    public ArrayList<Notification> loadNotifications() {
        File file = new File(NOTIFICATION_FILE);
        if (file.exists()) {
            try {
                CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Notification.class);
                return objectMapper.readValue(file, listType);
             //   notifications = objectMapper.readValue(file, new TypeReference<List<Notification>>() {});
            } catch (IOException e) {
               
                System.err.println("Error loading notifications: " + e.getMessage());
            }
        } return new ArrayList<>();
    }

    // Save notifications to the JSON file
    private void saveNotifications() {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(NOTIFICATION_FILE), notifications);
        } catch (IOException e) {
            System.err.println("Error saving notifications: " + e.getMessage());
        }
    }

    // Add a notification
    public void addNotification(String message , User reciever , User sender , String type , boolean actionable) {
        Notification notification = new Notification(message, reciever , sender , type , actionable);
        notifications.add(notification);
        saveNotifications();
        System.out.println("Notification added: "+message);
    }

    // Fetch unread notifications for a user
    public ArrayList<Notification> getNotificationsForUser(User user) {
       ArrayList<Notification> userNotifications = new ArrayList<>();
       for(Notification n : notifications){
           if(n.getReciever().getId().equals(user.getId())){
               userNotifications.add(n);
           }
       }
       return userNotifications;
    }
    public void removeNotification(Notification n){
        notifications.remove(n);
        saveNotifications();
    }
    public void clearNotificationForUser(User user){
        notifications.removeIf(n->n.getReciever().getId().equals(user.getId()));
    }

    // Mark a notification as read
   /* public void markAsRead(String notificationId) {
        notifications.stream()
                .filter(n -> n.getId().equals(notificationId))
                .findFirst()
                .ifPresent(n -> {
                    n.setRead(true);
                    saveNotifications();
                });
    }*/

    
}