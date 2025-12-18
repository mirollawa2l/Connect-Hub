/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Chats;

/**
 *
 * @author HP
 */
import userdatabasemanagement.UserDatabaseManagement; // FIX: Removed unused import java.time.LocalDateTime

public class Chat implements ChatInt{
    private String senderId;
    private String receiverId;
    private String messageContent;
    private final UserDatabaseManagement accountManager; // FIX: Made final - assigned once in constructor
   // private LocalDateTime timestamp;

    public Chat()
    {
                accountManager=new UserDatabaseManagement();

    }
    
    public Chat(String senderId, String receiverId, String messageContent) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.messageContent = messageContent;
        accountManager=new UserDatabaseManagement();
       // this.timestamp = timestamp;
    }

    @Override // FIX: Added missing @Override annotation
    public String getSenderId() {
        return senderId;
    }

    @Override // FIX: Added missing @Override annotation
    public String getReceiverId() {
        return receiverId;
    }

    @Override // FIX: Added missing @Override annotation
    public String getMessageContent() {
        return messageContent;
    }

    @Override
    public String toString() {
        return  accountManager.getUser(senderId).getUsername() +": " + messageContent;
    }

   
   
}