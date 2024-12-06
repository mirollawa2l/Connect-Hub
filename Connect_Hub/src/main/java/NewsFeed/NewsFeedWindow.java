package NewsFeed;

import javax.swing.*;
import java.awt.*;
import Content_Creation.Backend.Content;
import Content_Creation.Backend.ContentManagement;
import Content_Creation.Frontend.AddPostWindow;
import Content_Creation.Frontend.AddStoryWindow;
import friendManagment.Backend.ManageFriends;
import friendManagment.FrontEnd.FriendRequestWindow;
import friendManagment.FrontEnd.FriendSuggestionsWindow;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import profilemanagement.PostRepository;
import profilemanagement.ProfileGUI;
import profilemanagement.ProfileManager;
import profilemanagement.UserRepository;
import userdatabasemanagement.AccountManagment;
import userdatabasemanagement.CurrentUser;
import userdatabasemanagement.Login;
import userdatabasemanagement.User;
import userdatabasemanagement.UserDatabaseManagement;

/**
 *
 * @author mirol
 */
public class NewsFeedWindow extends javax.swing.JFrame {

    private JPanel postsPanel;
    private JScrollPane scrollPane;
    private JButton refreshButton;
    private DefaultComboBoxModel<String> model;
    private DefaultListModel<String> listModel;
    private ManageFriends friendManager;
    private UserDatabaseManagement accountManagement;
    private ContentManagement contentManager;
    private ArrayList<Content> friendsContent;
    private ProfileManager profileManager;
    private UserRepository userRepository;
    private PostRepository postRepository;
    private User user;

    public NewsFeedWindow() throws IOException {
        initComponents();

    user = CurrentUser.getInstance().getCurrentUser();
        model = new DefaultComboBoxModel<>();
        SelectFriend.setModel(model);
        
        listModel = new DefaultListModel<>();
        friendList.setModel(listModel);
        friendList = new JList<>(listModel);
        accountManagement = new UserDatabaseManagement();
        friendManager = new ManageFriends();
        contentManager = new ContentManagement();
        friendsContent = new ArrayList<>();
        userRepository = new UserRepository();
        postRepository = new PostRepository();
        profileManager = new ProfileManager(userRepository, postRepository);
        // Initialize components
        postsPanel = new JPanel();
        postsPanel.setLayout(new BoxLayout(postsPanel, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(postsPanel);
        refreshButton = new JButton("Refresh");

        //  refreshButton.addActionListener(e -> refreshNewsFeed());
        // Add components to the frame
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(refreshButton, BorderLayout.SOUTH);

        update();
        updateFriends();
        displayContents();

    }

    public void updateFriends() {

        if (friendManager.getFriends() != null) {
            for (User u : friendManager.getFriends()) {
                friendsContent.add(contentManager.getContent(u.getId()));
            }
        }
    }



    void displayContents() {
        postsPanel.removeAll(); // Clear previous content
        friendsContent.add(contentManager.getContent(user.getId()));
        for (Content content : friendsContent) {
            if(content==null)
            {
                continue;
            }
            else if (content.isStory() && content.isExpired()) {
                continue; // Skip expired stories
            }

            else 
            {
            // Create a panel for each post or story
            JPanel contentPanel = new JPanel();
            contentPanel.setLayout(new BorderLayout());
            contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
           // contentPanel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));

            contentPanel.setBackground(Color.BLUE);

            // Add the author's name
            JLabel authorLabel = new JLabel("Author: " + content.getAuthorId());
            authorLabel.setFont(new Font("Arial", Font.BOLD, 14));
            contentPanel.add(authorLabel, BorderLayout.NORTH);

            // Center content (text and/or image)
            JPanel centerPanel = new JPanel();
            centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
            centerPanel.setBackground(Color.WHITE);

            // Display text if present
            if (content.getContent() != null && !content.getContent().isEmpty()) {
                JTextArea contentArea = new JTextArea(content.getContent());
                contentArea.setLineWrap(true);
                contentArea.setWrapStyleWord(true);
                contentArea.setEditable(false);
                contentArea.setFont(new Font("Arial", Font.PLAIN, 14));
                contentArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                centerPanel.add(contentArea);
            }

            // Display image if present
            if (content.getImagePath() != null && !content.getImagePath().isEmpty()) {
                ImageIcon imageIcon = new ImageIcon(content.getImagePath());
                Image scaledImage = imageIcon.getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH); // Resize image
                JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
                imageLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                centerPanel.add(imageLabel);
            }
            

            contentPanel.add(centerPanel, BorderLayout.CENTER);

            // Add timestamp
            JLabel timestampLabel = new JLabel("Timestamp: " + content.getTimestamp());
            timestampLabel.setFont(new Font("Arial", Font.ITALIC, 12));
            contentPanel.add(timestampLabel, BorderLayout.SOUTH);
            
            // Add the content panel to the main panel
            postsPanel.add(contentPanel);
            postsPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing between contents
            }
        }
            
        friendsContent.remove(contentManager.getContent(user.getId()));
        postsPanel.revalidate();
        postsPanel.repaint();
    }
        

    public void update() {
        model.removeAllElements();
        listModel.clear();

           friendList.revalidate();
          friendList.repaint();
          model.addElement("Search");
        ArrayList<User> friends=friendManager.getFriends();
        if(friends!=null){
        for(User friend:friends){
            String username=friend.getUsername();
            String status=friend.getStatus();
            String displayedText=username + " (" + status + ")";
             model.addElement(displayedText);
            listModel.addElement(friend.getUsername());
        }

        friendList.revalidate();
        friendList.repaint();

    }
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        addFriend = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        friendList = new javax.swing.JList<>();
        SelectFriend = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        Remove = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        addPostBtn = new javax.swing.JButton();
        addStoryBtn = new javax.swing.JButton();
        friendSuggestion = new javax.swing.JButton();
        friendRequest = new javax.swing.JButton();
        logoutBtn = new javax.swing.JButton();
        updateProfile = new javax.swing.JButton();
        Block = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        addFriend.setText("Add Friend");
        addFriend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addFriendActionPerformed(evt);
            }
        });

        friendList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(friendList);

        SelectFriend.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        SelectFriend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SelectFriendActionPerformed(evt);
            }
        });

        jLabel2.setBackground(new java.awt.Color(0, 102, 102));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 102, 102));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Friend List ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 657, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        Remove.setText("Remove");
        Remove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RemoveActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(0, 102, 102));

        addPostBtn.setText("Add Post");
        addPostBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addPostBtnActionPerformed(evt);
            }
        });

        addStoryBtn.setText("Add Story");
        addStoryBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addStoryBtnActionPerformed(evt);
            }
        });

        friendSuggestion.setText("Friend Suggestion");
        friendSuggestion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                friendSuggestionActionPerformed(evt);
            }
        });

        friendRequest.setText("Friend Request");
        friendRequest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                friendRequestActionPerformed(evt);
            }
        });

        logoutBtn.setText("Logout");
        logoutBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutBtnActionPerformed(evt);
            }
        });

        updateProfile.setText("Update Profile");
        updateProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateProfileActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(addPostBtn)
                .addGap(18, 18, 18)
                .addComponent(addStoryBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(friendSuggestion)
                .addGap(18, 18, 18)
                .addComponent(friendRequest)
                .addGap(12, 12, 12)
                .addComponent(updateProfile)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(logoutBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addPostBtn)
                    .addComponent(addStoryBtn)
                    .addComponent(friendSuggestion)
                    .addComponent(friendRequest)
                    .addComponent(logoutBtn)
                    .addComponent(updateProfile))
                .addGap(15, 15, 15))
        );

        Block.setText("Block ");
        Block.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BlockActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(Remove)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(Block))
                            .addComponent(addFriend, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(SelectFriend, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(SelectFriend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Block, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(Remove, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7)
                        .addComponent(addFriend)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 66, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addFriendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addFriendActionPerformed
        boolean found=false;
        String username = JOptionPane.showInputDialog("Search");

        for (User u : accountManagement.loadUsers()) {
            if (username.equals(u.getUsername())) {
                friendManager.AddFriend(u);
            
        
        update();
        updateFriends();
        System.out.println("found");
        found=true;
            }
        }

        if(!found)
         JOptionPane.showMessageDialog(null,"No user found!","Error",JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_addFriendActionPerformed

    private void SelectFriendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SelectFriendActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SelectFriendActionPerformed

    private void RemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RemoveActionPerformed
         boolean found=false;
        String username = (String) SelectFriend.getSelectedItem();
        if (username.equals("Search")) {
            JOptionPane.showMessageDialog(this, "Please select a user first.");
        } else { if(friendManager.getFriends()!=null){
            for(User friend:friendManager.getFriends())
            if(username.equals(friend.getUsername())){
                friendManager.RemoveFriend(friend);
                update();
                 updateFriends();
            found=true;}}
        else
         JOptionPane.showMessageDialog(null,"User has no friends!","Error",JOptionPane.INFORMATION_MESSAGE);
        }
        if(!found)
           JOptionPane.showMessageDialog(null,"No user found!","Error",JOptionPane.INFORMATION_MESSAGE);  
    }//GEN-LAST:event_RemoveActionPerformed

    private void addPostBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addPostBtnActionPerformed
        // TODO add your handling code here:
        AddPostWindow w = new AddPostWindow(this, true);
        w.setVisible(true);
    }//GEN-LAST:event_addPostBtnActionPerformed

    private void addStoryBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addStoryBtnActionPerformed
        // TODO add your handling code here:
        AddStoryWindow w = new AddStoryWindow(this, true);
        w.setVisible(true);
    }//GEN-LAST:event_addStoryBtnActionPerformed

    private void friendSuggestionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_friendSuggestionActionPerformed
        FriendSuggestionsWindow friendSuggestionWindow = new FriendSuggestionsWindow();
        friendSuggestionWindow.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_friendSuggestionActionPerformed

    private void friendRequestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_friendRequestActionPerformed
        FriendRequestWindow friendRequestWindow = new FriendRequestWindow();
        friendRequestWindow.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_friendRequestActionPerformed

    private void BlockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BlockActionPerformed
        boolean found=false;
        String username = (String) SelectFriend.getSelectedItem();
        if (username.equals("Search")) {
            JOptionPane.showMessageDialog(this, "Please select a user first.");
        } else {

            if(friendManager.getFriends()!=null){
            for(User friend:friendManager.getFriends())
            if(username.equals(friend.getUsername())){
                friendManager.BlockFriend(friend);
                 update();
                updateFriends();
                 found=true;
                
            }}
            else JOptionPane.showMessageDialog(null,"User has no friends!","Error",JOptionPane.INFORMATION_MESSAGE);
                if(!found)
           JOptionPane.showMessageDialog(null,"No user found!","Error",JOptionPane.INFORMATION_MESSAGE);  }

    }//GEN-LAST:event_BlockActionPerformed

    private void updateProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateProfileActionPerformed
        // TODO add your handling code here:
        ProfileGUI w = new ProfileGUI(profileManager, user);
        w.setVisible(true);

    }//GEN-LAST:event_updateProfileActionPerformed

    private void logoutBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutBtnActionPerformed
        // TODO add your handling code here:
        AccountManagment w=new AccountManagment();
        w.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_logoutBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NewsFeedWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewsFeedWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewsFeedWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewsFeedWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new NewsFeedWindow().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(NewsFeedWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Block;
    private javax.swing.JButton Remove;
    private javax.swing.JComboBox<String> SelectFriend;
    private javax.swing.JButton addFriend;
    private javax.swing.JButton addPostBtn;
    private javax.swing.JButton addStoryBtn;
    private javax.swing.JList<String> friendList;
    private javax.swing.JButton friendRequest;
    private javax.swing.JButton friendSuggestion;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton logoutBtn;
    private javax.swing.JButton updateProfile;
    // End of variables declaration//GEN-END:variables
}
