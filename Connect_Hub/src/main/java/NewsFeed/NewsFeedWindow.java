/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */


package NewsFeed;

import javax.swing.*;
import java.awt.*;
import Content_Creation.Backend.Content;
import Content_Creation.Backend.ContentManagement;
import Content_Creation.Frontend.AddPostWindow;
import Content_Creation.Frontend.AddStoryWindow;
import Search.SearchUserWindow;
import Groups_FrontEnd.ViewGroups;
import friendManagment.Backend.ManageFriends;
import friendManagment.FrontEnd.FriendListWindow;
import friendManagment.FrontEnd.FriendRequestWindow;
import friendManagment.FrontEnd.FriendSuggestionsWindow;
import profilemanagement.PostRepository;
import profilemanagement.ProfileManager;
import profilemanagement.UserRepository;
import userdatabasemanagement.CurrentUser;
import userdatabasemanagement.User;
import userdatabasemanagement.UserDatabaseManagement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import profilemanagement.ProfileGUI;
import userdatabasemanagement.AccountManagment;


/**
 * News Feed window for displaying posts and stories from friends.
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
  this.setLocation(250, 300);
        setTitle("News Feed");
        user = CurrentUser.getInstance().getCurrentUser();

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

        // Configure panel and layout
        jPanel1.setBackground(Color.WHITE);
        jPanel1.setLayout(new BorderLayout());
        jPanel1.add(scrollPane, BorderLayout.CENTER);

        // Ensure the main layout is set up
        add(refreshButton, BorderLayout.SOUTH);


        postsPanel.setPreferredSize(new Dimension(900, 900));

     postsPanel.setBackground(Color.WHITE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize postsPanel and scrollPane
//    postsPanel = new JPanel();
    
    jPanel1.setBackground(Color.WHITE);
//
//    postsPanel.setPreferredSize(new Dimension(450, 450));
//    postsPanel.setBackground(Color.WHITE);
//
//    postsPanel.setLayout(new BoxLayout(postsPanel, BoxLayout.Y_AXIS));
    scrollPane = new JScrollPane(postsPanel);

    // Ensure displayPanel is set up correctly
    jPanel1.setLayout(new BorderLayout());
    jPanel1.add(scrollPane, BorderLayout.CENTER);

    // Call update methods
 
    updateFriendsContent();
    displayContents();

    // Revalidate and repaint the main frame
    this.revalidate();
    this.repaint();

        updateFriendsContent();
        displayContents();
        revalidate();
        repaint();
    }

    public void updateFriendsContent() {
        if (friendManager.loadFriends(user.getId()) != null) {
            for (User u : friendManager.getFriends()) {
                friendsContent.addAll(contentManager.getcontentByAuthorId(u.getId()));
            }
        }
    }

void displayContents() {
    contentManager.load();
    postsPanel.removeAll(); // Clear previous content
    friendsContent.clear(); // Ensure the list starts empty
    updateFriendsContent();
    
    
    postsPanel.revalidate();
    postsPanel.repaint();

    // Retrieve unique content
    Set<String> uniqueIds = new HashSet<>();
    ArrayList<Content> deduplicatedContent = new ArrayList<>();
    for (Content content : contentManager.getcontentByAuthorId(user.getId())) {
        if (uniqueIds.add(content.getContentId())) { // Add only unique items
            deduplicatedContent.add(content);
        }
    }
    friendsContent.addAll(deduplicatedContent);

    System.out.println("friendsContent size: " + friendsContent.size());

    // Create separate panels for stories and posts
    JPanel storiesPanel = new JPanel();
    storiesPanel.setLayout(new BoxLayout(storiesPanel, BoxLayout.Y_AXIS));
    storiesPanel.setBorder(BorderFactory.createTitledBorder("Stories"));

    JPanel postsDisplayPanel = new JPanel();
    postsDisplayPanel.setLayout(new BoxLayout(postsDisplayPanel, BoxLayout.Y_AXIS));
    postsDisplayPanel.setBorder(BorderFactory.createTitledBorder("Posts"));

    for (Content content : friendsContent) {
        if (content == null) {
            System.out.println("Null content found, skipping...");
            continue;
        }

        // Skip expired stories
        if (content.isStory() && content.isExpired()) {
            System.out.println("Expired story found, skipping...");
            continue;
        }

        System.out.println("Displaying content: " + content);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.setBackground(Color.LIGHT_GRAY);
        JLabel authorLabel = new JLabel("Author: " + accountManagement.getUser(content.getAuthorId()).getUsername() + " Time: " + content.getTimestamp());
        contentPanel.add(authorLabel, BorderLayout.NORTH);

        // Display text content if available
        if (content.getContent() != null && !content.getContent().isEmpty()) {
            JTextArea contentArea = new JTextArea(content.getContent());
            contentArea.setLineWrap(true);
            contentArea.setWrapStyleWord(true);
            contentArea.setEditable(false); // Make the content non-editable
            contentPanel.add(new JScrollPane(contentArea), BorderLayout.CENTER);
        }

        // Display image if available
        if (content.getImagePath() != null && !content.getImagePath().isEmpty()) {
            try {
                ImageIcon imageIcon = new ImageIcon(content.getImagePath()); // Load image from path
                JLabel imageLabel = new JLabel();
                imageLabel.setIcon(imageIcon);
                contentPanel.add(imageLabel, BorderLayout.SOUTH);
            } catch (Exception e) {
                System.err.println("Error loading image for content: " + content.getContentId());
                e.printStackTrace();
            }
        }

        // Add content to the appropriate panel
        if (content.isStory()) {
            storiesPanel.add(contentPanel);
        } else {
            postsDisplayPanel.add(contentPanel);
        }
    }

    // Combine stories and posts panels
    JPanel combinedPanel = new JPanel(new BorderLayout());
    combinedPanel.add(storiesPanel, BorderLayout.NORTH);
    combinedPanel.add(postsDisplayPanel, BorderLayout.CENTER);

    // Add combined panel to the main postsPanel
    postsPanel.setLayout(new BorderLayout());
    postsPanel.add(combinedPanel, BorderLayout.CENTER);

    postsPanel.revalidate();
    postsPanel.repaint();
}


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        addPostBtn = new javax.swing.JButton();
        addStoryBtn = new javax.swing.JButton();
        friendSuggestion = new javax.swing.JButton();
        friendRequest = new javax.swing.JButton();
        logoutBtn = new javax.swing.JButton();
        updateProfile = new javax.swing.JButton();
        friendList = new javax.swing.JButton();
        refresh = new javax.swing.JButton();
        viewGroups = new javax.swing.JButton();
        groupSuggestions = new javax.swing.JButton();

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("News Feed");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);


        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)

            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)

            .addGap(0, 886, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 492, Short.MAX_VALUE)

        );

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

        friendList.setText("FriendList");
        friendList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                friendListActionPerformed(evt);
            }
        });

        refresh.setText("Refresh");
        refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshActionPerformed(evt);
            }
        });


        jButton1.setText("Search User");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Search Group");

        viewGroups.setText("View groups");
        viewGroups.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewGroupsActionPerformed(evt);
            }
        });

        groupSuggestions.setText("Group Suggestions");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(addPostBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)

                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(addPostBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)

                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addStoryBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(friendSuggestion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(friendRequest)

                .addGap(18, 18, 18)
                .addComponent(friendList, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(refresh)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(18, 18, 18)
                .addComponent(updateProfile)
                .addGap(18, 18, 18)

                .addGap(29, 29, 29)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(friendRequest)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(friendList, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(refresh)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(viewGroups)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(groupSuggestions, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(updateProfile)

                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(logoutBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
            .addGroup(jPanel2Layout.createSequentialGroup()

                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addPostBtn)
                    .addComponent(addStoryBtn)
                    .addComponent(friendSuggestion)
                    .addComponent(friendRequest)
                    .addComponent(logoutBtn)

                    .addComponent(friendList)
                    .addComponent(refresh)
                    .addComponent(jButton1)
                    .addComponent(jButton2))

                    .addComponent(refresh)
                    .addComponent(friendList))

                    .addComponent(friendList)
                    .addComponent(viewGroups)
                    .addComponent(groupSuggestions))
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)

            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 16, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())

            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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


    }//GEN-LAST:event_friendSuggestionActionPerformed

    private void friendRequestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_friendRequestActionPerformed
        FriendRequestWindow friendRequestWindow = new FriendRequestWindow();
        friendRequestWindow.setVisible(true);


    }//GEN-LAST:event_friendRequestActionPerformed

    private void updateProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateProfileActionPerformed
        // TODO add your handling code here:
        ProfileGUI w = new ProfileGUI(profileManager, user);
        w.setVisible(true);
       
    }//GEN-LAST:event_updateProfileActionPerformed

    private void logoutBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutBtnActionPerformed

        try {
            // TODO add your handling code here:
            accountManagement.updateStatus(CurrentUser.getInstance().getCurrentUser().getId(), "offline");
        } catch (IOException ex) {
            Logger.getLogger(NewsFeedWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

        AccountManagment w = new AccountManagment();
        w.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_logoutBtnActionPerformed

    private void friendListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_friendListActionPerformed
         FriendListWindow friendListWindow = new  FriendListWindow ();
        friendListWindow.setVisible(true);
    }//GEN-LAST:event_friendListActionPerformed

    private void refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshActionPerformed
        // TODO add your handling code here:
        displayContents();
    }//GEN-LAST:event_refreshActionPerformed


    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        SearchUserWindow sw= new SearchUserWindow();
        sw.setVisible(true);
        sw.setLocationRelativeTo(null);
    }//GEN-LAST:event_jButton1ActionPerformed
    private void viewGroupsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewGroupsActionPerformed
        // TODO add your handling code here
        ViewGroups viewGroups= new ViewGroups();
        viewGroups.setVisible(true);
        
    }//GEN-LAST:event_viewGroupsActionPerformed

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
    private javax.swing.JButton addPostBtn;
    private javax.swing.JButton addStoryBtn;
    private javax.swing.JButton friendList;
    private javax.swing.JButton friendRequest;
    private javax.swing.JButton friendSuggestion;

    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;

    private javax.swing.JButton groupSuggestions;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton logoutBtn;
    private javax.swing.JButton refresh;
    private javax.swing.JButton updateProfile;
    private javax.swing.JButton viewGroups;
    // End of variables declaration//GEN-END:variables
}
