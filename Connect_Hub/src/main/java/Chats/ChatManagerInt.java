/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Chats;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author mirol
 */
public interface ChatManagerInt {

    public abstract ArrayList<Chat> loadChats();

    public abstract List<Chat> getChatHistory(String user1, String user2);

    public abstract Set<String> getChattedUsers();

    public abstract List<Chat> getAllMessages();
}
