/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Groups_Backend;

import java.util.ArrayList;
import userdatabasemanagement.User;

/**
 *
 * @author sherrygirguis
 */
public interface GroupSuggestionsManagerInterfcae {
    public abstract ArrayList<GroupSuggestion> generateSuggestions(Group group,User user);
    public abstract void acceptGroupSuggestion(GroupSuggestion suggestion,User user);
    public abstract ArrayList< GroupSuggestion >getSuggestions();
    public abstract GroupSuggestion getSuggestion(Group group);
     
}
