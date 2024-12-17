/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Chats;

/**
 *
 * @author HP
 */
import java.time.LocalDateTime;

public class Chat {
    private String senderId;
    private String receiverId;
    private String messageContent;
   // private LocalDateTime timestamp;

    public Chat(String senderId, String receiverId, String messageContent) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.messageContent = messageContent;
       // this.timestamp = timestamp;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public String getMessageContent() {
        return messageContent;
    }

    @Override
    public String toString() {
        return "{" + "senderId=" + senderId +"[ " + messageContent + ']';
    }

   
   
}