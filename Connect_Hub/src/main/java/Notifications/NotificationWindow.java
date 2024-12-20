/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Notifications;

/**
 *
 * @author sherrygirguis
 */
import Content_Creation.Backend.ContentManagement;
import Content_Creation.Frontend.ViewPost;
import PostInteraction.CommentsWindow;
import Chats.ChatManager;
import Chats.ChatWindow;
import friendManagment.Backend.FriendRequest;
import friendManagment.Backend.ManageFriendRequests;
import java.awt.BorderLayout;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import userdatabasemanagement.CurrentUser;
import userdatabasemanagement.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import userdatabasemanagement.UserDatabaseManagement;
public class NotificationWindow extends JFrame{
      private DefaultListModel<Notification> notificationListModel;
    private JList<Notification> notificationList;
    private NotificationManager notificationManager;
    private User currentUser;
    private ContentManagement contentManager;
    private UserDatabaseManagement accountManager;
    private ViewPost viewPost;
    private boolean running = true; // Flag to stop the thread when the window is closed
    
public NotificationWindow(NotificationManager notificationManager, User currentUser) {
        this.notificationManager = notificationManager;
        this.currentUser = currentUser;
         viewPost=new ViewPost();
         contentManager=new ContentManagement();

        setTitle("Notifications for"+currentUser.getUsername());
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();}
        
         private void initComponents() {
        notificationListModel = new DefaultListModel<>();
        notificationList = new JList<>(notificationListModel);
        notificationList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(notificationList);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(this::refreshNotifications);

        JButton actionButton = new JButton("Take Action");
        actionButton.addActionListener(this::takeAction);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(refreshButton);
        buttonPanel.add(actionButton);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        refreshNotifications(null); // Initial load
    }
    private void refreshNotifications(ActionEvent evt) {
   
        }
    private void loadNotifications()
{
 notificationListModel.clear();
        ArrayList<Notification> notifications = NotificationManager.getInstance().getNotificationsForUser(currentUser);
        for (Notification notification : notifications) {
            notificationListModel.addElement(notification);
}}


    private void takeAction(ActionEvent evt) {
        Notification selectedNotification = notificationList.getSelectedValue();
        if (selectedNotification == null || !selectedNotification.isActionable()) {
            JOptionPane.showMessageDialog(this, "Select an actionable notification!");
            return;
        }

        if ("friendRequest".equals(selectedNotification.getType())) {
            String[] options = {"Accept", "Decline"};
            int choice = JOptionPane.showOptionDialog(
                this, "Action for: " + selectedNotification.getMessage(),
                "Friend Request", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]
            );

            if (choice == 0) { // Accept
                new ManageFriendRequests().acceptRequest(
                    new FriendRequest(currentUser, selectedNotification.getSender(), "accepted")
                );
                NotificationManager.getInstance().addNotification("Your Friend request was accepted by"+selectedNotification.getReciever().getUsername(),selectedNotification.getSender(), currentUser, "response", false);
                NotificationManager.getInstance().addNotification("You accept "+selectedNotification.getSender().getUsername()+"Request !", selectedNotification.getReciever(), selectedNotification.getSender(), "response", false);
            } else if (choice == 1) { // Decline
                new ManageFriendRequests().declineRequest(
                    new FriendRequest(currentUser, selectedNotification.getSender(), "declined")
                );
                NotificationManager.getInstance().addNotification("Your Friend Request was declined by "+selectedNotification.getReciever().getUsername(),selectedNotification.getSender(), currentUser, "response", false);
                NotificationManager.getInstance().addNotification("You declined "+selectedNotification.getSender().getUsername()+"Request !", selectedNotification.getReciever(), selectedNotification.getSender(), "response", false);
            }
            NotificationManager.getInstance().removeNotification(selectedNotification);
            refreshNotifications(null); // Refresh list
        }

//        if ("comment".equals(selectedNotification.getType())){
//          viewPost.showPost();
//        }
//        if ("like".equals(selectedNotification.getType())){
//          viewPost.showPost();
//        }
//        
            
            
    }
  

    private void startNotificationThread() {
        Thread notificationThread = new Thread(() -> {
            while (running) {
                try {
                    // Update notifications every 2 seconds
                    Thread.sleep(2000);

                    // Update the UI on the Event Dispatch Thread (EDT)
                    SwingUtilities.invokeLater(this::loadNotifications);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("Notification thread interrupted: " + e.getMessage());
                }
            }
        });
        notificationThread.setDaemon(true); // Ensures thread stops when the application exits
        notificationThread.start();
    }

      @Override
    public void dispose() {
        running = false; // Stop the thread when the window is closed
        super.dispose();

//         else if ("chat".equals(selectedNotification.getType())) {
//        String[] options = {"Reply", "Decline"};
//        int choice = JOptionPane.showOptionDialog(
//            this, "Action for: " + selectedNotification.getMessage(),
//            "Chat", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]
//        );
//
//        if (choice == 0) { // Reply
//            // Open the chat window with the sender of the message
//            String senderId = selectedNotification.getSender().getId();
//            String receiverId = selectedNotification.getReciever().getId();
//            
//            // Open the chat window for the current user and the sender of the message
//            
//            ChatWindow chatWindow = new ChatWindow(selectedNotification.getReciever(),selectedNotification.getSender() );
//            ChatManager.getInstance().getChatHistory(selectedNotification.getSender().getId(), selectedNotification.getReciever().getId());
//            chatWindow.setVisible(true);
//
//            // Remove notification after taking action
//            NotificationManager.getInstance().removeNotification(selectedNotification);
//            refreshNotifications(null); // Refresh list
//        } else if (choice == 1) { // Decline
//            // You can handle "Decline" as ignoring or simply removing the notification
//            NotificationManager.getInstance().removeNotification(selectedNotification);
//            refreshNotifications(null); // Refresh list
//        }
//
//    }
}

}
    

