/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Chats;

/**
 *
 * @author HP
 */
import Notifications.NotificationManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import userdatabasemanagement.User;

public class ChatWindow extends JFrame {
    private User currentUser;
    private User chatWithUser;
    private JTextArea chatArea;
    private JTextField messageField;
    private JButton sendButton;
private ChatManager chatManager;
    public ChatWindow(User currentUser, User chatWithUser) {
        this.currentUser = currentUser;
        this.chatWithUser = chatWithUser;
        chatManager=new ChatManager();
          chatManager = ChatManager.getInstance();
        setTitle("Chat with " + chatWithUser.getUsername());
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Layout setup
        setLayout(new BorderLayout());

        // Chat area to show messages
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        add(scrollPane, BorderLayout.CENTER);

        // Message input field and send button
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        messageField = new JTextField();
        panel.add(messageField, BorderLayout.CENTER);

        sendButton = new JButton("Send");
        panel.add(sendButton, BorderLayout.EAST);

        add(panel, BorderLayout.SOUTH);

        // Load previous chat history
        loadChatHistory();

        // Send button action
        sendButton.addActionListener(e -> sendMessage());
    }

    private void loadChatHistory() {
        // Get previous chat messages from ChatManager
        List<Chat> messages = chatManager.getChatHistory(currentUser.getId(), chatWithUser.getId());
        System.out.println("Messages: "+messages);
        // Display all previous messages in the chat area
        for (Chat message : messages) {
            chatArea.append(message + "\n");
        }
    }

    private void sendMessage() {
        String message = messageField.getText();
        NotificationManager.getInstance().addNotification("You have a new message", currentUser, chatWithUser, "chat", true);
        if (message.isEmpty()) {
            return;
        }

        // Save the new message to chat history
        ChatManager.getInstance().writeMessage(currentUser, chatWithUser, message);

        // Update chat area with the new message
        chatArea.append(currentUser.getUsername() + ": " + message + "\n");
        messageField.setText(""); // Clear the message input field
    }
}