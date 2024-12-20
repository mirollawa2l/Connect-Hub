/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package NewsFeed;

import Content_Creation.Backend.Content;
import Content_Creation.Backend.ContentManagement;
import Content_Creation.Backend.Post;
import Content_Creation.Frontend.AddPostWindow;
import Content_Creation.Frontend.AddStoryWindow;
import Groups_FrontEnd.GroupSuggestionWindow;
import Notifications.NotificationManager;
import Search.SearchGroupWindow;
import Search.SearchUserWindow;
import friendManagment.Backend.ManageFriends;
import friendManagment.FrontEnd.FriendListWindow;
import friendManagment.FrontEnd.FriendRequestWindow;
import friendManagment.FrontEnd.FriendSuggestionsWindow;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import profilemanagement.PostRepository;
import profilemanagement.ProfileGUI;
import profilemanagement.ProfileManager;
import profilemanagement.UserRepository;
import userdatabasemanagement.AccountManagment;
import userdatabasemanagement.CurrentUser;
import userdatabasemanagement.User;
import userdatabasemanagement.UserDatabaseManagement;
import Groups_FrontEnd.ViewGroups;
import Notifications.NotificationWindow;
import PostInteraction.AddCommentDialog;
import PostInteraction.CommentProvider;
import PostInteraction.CommentsWindow;
import PostInteraction.ContentAdapter;
import java.awt.FlowLayout;

/**
 *
 * @author Yara
 */
public class NewsFeedWindow extends javax.swing.JFrame {

    /**
     * Creates new form NewsFeedWindow
     */
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
    private NotificationManager notificationManager;
    public NewsFeedWindow() throws IOException {
         initComponents();

        user = CurrentUser.getInstance().getCurrentUser();

        accountManagement = new UserDatabaseManagement();
        friendManager = new ManageFriends();
        contentManager = new ContentManagement();
        friendsContent = new ArrayList<>();
        userRepository = new UserRepository();
        postRepository = new PostRepository();
        profileManager = new ProfileManager(userRepository, postRepository);
        System.out.println("");
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

        postsPanel.setPreferredSize(new Dimension(1200, 1200));
     postsPanel.setBackground(Color.WHITE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    jPanel1.setBackground(Color.WHITE);

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

void displayContents() throws IOException {
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

        // Main panel for the post
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.setBackground(Color.LIGHT_GRAY);

        // Header: Author and Timestamp
        JLabel authorLabel = new JLabel("Author: " + accountManagement.getUser(content.getAuthorId()).getUsername() +
                " Time: " + content.getTimestamp());
        contentPanel.add(authorLabel, BorderLayout.NORTH);

        // Content: Text area for post content
        if (content.getContent() != null && !content.getContent().isEmpty()) {
            JTextArea contentArea = new JTextArea(content.getContent());
            contentArea.setLineWrap(true);
            contentArea.setWrapStyleWord(true);
            contentArea.setEditable(false); // Make the content non-editable
            contentPanel.add(new JScrollPane(contentArea), BorderLayout.CENTER);
        }

        // Image: Display if available
        if (content.getImagePath() != null && !content.getImagePath().isEmpty()) {
            try {
                ImageIcon imageIcon = new ImageIcon(content.getImagePath()); // Load image from path
                JLabel imageLabel = new JLabel();
                imageLabel.setIcon(imageIcon);
                contentPanel.add(imageLabel, BorderLayout.CENTER);
            } catch (Exception e) {
                System.err.println("Error loading image for content: " + content.getContentId());
                e.printStackTrace();
            }
        }

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton likeButton = new JButton("Like");
        JButton showCommentsButton = new JButton("Show Comments");
        JButton addCommentButton = new JButton("Add Comment");

        // Add action listeners
        likeButton.addActionListener(e -> handleLike(content));
        showCommentsButton.addActionListener(e -> showComments(content));
        addCommentButton.addActionListener(e -> addComment(content));

        // Add buttons to the button panel
        buttonPanel.add(likeButton);
        buttonPanel.add(showCommentsButton);
        buttonPanel.add(addCommentButton);

        // Add the button panel to the content panel
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add the content panel to the postsPanel
        postsPanel.add(contentPanel);
    }

    // Refresh the UI
    postsPanel.revalidate();
    postsPanel.repaint();
}
private void handleLike(Content content) {
    System.out.println("Liked content: " + content.getContentId());
    
    // Add the current user's ID to the likes list if not already present
    String userId= user.getId();  // Assuming 'user' is the current user
    if (content instanceof Post) {
        Post post = (Post) content;
        post.addLike(userId);  // Add like for the post
    }

    // Save the updated content (likes and comments)
    contentManager.save();  // Make sure save method in ContentManagement persists changes
    System.out.println("Like added and content saved.");
     NotificationManager.getInstance().addNotification(user+" liked your post !", accountManagement.getUser(content.getAuthorId() ) , user ,"like" ,true);
    System.out.println("notification for reciever added "+accountManagement.getUser(content.getAuthorId()).getUsername());

    // Refresh the UI to show updated like count
    postsPanel.revalidate();
    postsPanel.repaint();
}


private void showComments(Content content) {
    System.out.println("Showing comments for content: " + content.getContentId());
    // Open a new window to display comments for this post
CommentsWindow commentsWindow = new CommentsWindow(new ContentAdapter(content));
    commentsWindow.setVisible(true);
}
private void addComment(Content content) {
    System.out.println("Adding comment to content: " + content.getContentId());
    
    // Open a dialog to add the comment
    AddCommentDialog addCommentDialog = new AddCommentDialog(this, true, content);
    addCommentDialog.setVisible(true);
    System.out.println("comment added");
    NotificationManager.getInstance().addNotification(user+" added a comment on your post in !", accountManagement.getUser(content.getAuthorId() ) , user ,"comment" ,true);
    System.out.println("notification for reciever added "+accountManagement.getUser(content.getAuthorId()).getUsername());
    
    // After the comment is added, save the updated content
    if (content instanceof Post) {
        Post post = (Post) content;
        contentManager.save();  // Save the updated content (including the new comment)
    }

    System.out.println("Comment added and content saved.");
    // Refresh the UI to reflect the new comment
    postsPanel.revalidate();
    postsPanel.repaint();
}


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
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
        searchUserBtn = new javax.swing.JButton();
        searchGroupBtn = new javax.swing.JButton();
        groupsBtn = new javax.swing.JButton();
        groupSuggestionsBtn = new javax.swing.JButton();
        notificationtn = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("News Feed");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1267, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 260, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(0, 102, 102));

        addPostBtn.setText("AddPost");
        addPostBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addPostBtnActionPerformed(evt);
            }
        });

        addStoryBtn.setText("AddStory");
        addStoryBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addStoryBtnActionPerformed(evt);
            }
        });

        friendSuggestion.setText("FriendSuggestion");
        friendSuggestion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                friendSuggestionActionPerformed(evt);
            }
        });

        friendRequest.setText("FriendRequest");
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

        updateProfile.setText("UpdateProfile");
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

        searchUserBtn.setText("SearchUser");
        searchUserBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchUserBtnActionPerformed(evt);
            }
        });

        searchGroupBtn.setText("SearchGroup");
        searchGroupBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchGroupBtnActionPerformed(evt);
            }
        });

        groupsBtn.setText("Groups");
        groupsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                groupsBtnActionPerformed(evt);
            }
        });

        groupSuggestionsBtn.setText("GroupSuggestions");
        groupSuggestionsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                groupSuggestionsBtnActionPerformed(evt);
            }
        });

        notificationtn.setText("Notifications");
        notificationtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                notificationtnActionPerformed(evt);
            }
        });

        jButton1.setText("Chats");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(addPostBtn)
                .addGap(2, 2, 2)
                .addComponent(addStoryBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(friendSuggestion, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(friendRequest, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(friendList)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(groupsBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(groupSuggestionsBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(notificationtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchUserBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchGroupBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(updateProfile)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(logoutBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(refresh)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addPostBtn)
                    .addComponent(addStoryBtn)
                    .addComponent(friendSuggestion)
                    .addComponent(friendRequest)
                    .addComponent(logoutBtn)
                    .addComponent(updateProfile)
                    .addComponent(friendList)
                    .addComponent(refresh)
                    .addComponent(searchUserBtn)
                    .addComponent(searchGroupBtn)
                    .addComponent(groupsBtn)
                    .addComponent(groupSuggestionsBtn)
                    .addComponent(notificationtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(113, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 110, Short.MAX_VALUE))
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

    private void updateProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateProfileActionPerformed
        // TODO add your handling code here:
        ProfileGUI w = new ProfileGUI(profileManager, user);
        w.setVisible(true);

    }//GEN-LAST:event_updateProfileActionPerformed

    private void friendListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_friendListActionPerformed
        FriendListWindow friendListWindow = new  FriendListWindow ();
        friendListWindow.setVisible(true);
    }//GEN-LAST:event_friendListActionPerformed

    private void refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshActionPerformed
        try {
            // TODO add your handling code here:
            displayContents();
        } catch (IOException ex) {
            Logger.getLogger(NewsFeedWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_refreshActionPerformed

    private void searchUserBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchUserBtnActionPerformed
        // TODO add your handling code here:
        SearchUserWindow sw= new SearchUserWindow();
        sw.setVisible(true);
        sw.setLocationRelativeTo(null);
    }//GEN-LAST:event_searchUserBtnActionPerformed

    private void searchGroupBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchGroupBtnActionPerformed
        // TODO add your handling code here:
         SearchGroupWindow sgw= new SearchGroupWindow();
        sgw.setVisible(true);
        sgw.setLocationRelativeTo(null);
    }//GEN-LAST:event_searchGroupBtnActionPerformed

    private void groupsBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_groupsBtnActionPerformed
        // TODO add your handling code here:
        ViewGroups w= new ViewGroups();
        w.setVisible(true);
        w.setLocationRelativeTo(null);
    }//GEN-LAST:event_groupsBtnActionPerformed

    private void groupSuggestionsBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_groupSuggestionsBtnActionPerformed
        // TODO add your handling code here:
        GroupSuggestionWindow w=new GroupSuggestionWindow();
        w.setVisible(true);
        w.setLocationRelativeTo(null); 
    }//GEN-LAST:event_groupSuggestionsBtnActionPerformed

    private void notificationtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_notificationtnActionPerformed
//notificationManager = new ManageFriendRequests().getNotificationManager();
NotificationWindow notificationWindow= new NotificationWindow(NotificationManager.getInstance(), CurrentUser.getInstance().getCurrentUser());
notificationWindow.setVisible(true);


        // TODO add your handling code here:
    }//GEN-LAST:event_notificationtnActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
  FriendListWindow f = new FriendListWindow();
    f.setVisible(true);

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

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
    private javax.swing.JButton groupSuggestionsBtn;
    private javax.swing.JButton groupsBtn;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton logoutBtn;
    private javax.swing.JButton notificationtn;
    private javax.swing.JButton refresh;
    private javax.swing.JButton searchGroupBtn;
    private javax.swing.JButton searchUserBtn;
    private javax.swing.JButton updateProfile;
    // End of variables declaration//GEN-END:variables
}
