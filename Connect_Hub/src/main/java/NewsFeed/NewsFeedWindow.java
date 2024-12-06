
package NewsFeed;


import javax.swing.*;
import java.awt.*;
import Content_Creation.Backend.Content;
import Content_Creation.Backend.ContentManagement;
import Content_Creation.Frontend.AddPostWindow;
import Content_Creation.Frontend.AddStoryWindow;
import friendManagment.Backend.ManageFriendRequests;
import friendManagment.Backend.ManageFriends;
import friendManagment.FrontEnd.FriendRequestWindow;
import friendManagment.FrontEnd.FriendSuggestionsWindow;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
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
private  DefaultComboBoxModel<String> model ;
private DefaultListModel<String> listModel ;
private JList<String> list;
private ManageFriends friendManager;
private UserDatabaseManagement  accountManagement;
private ContentManagement contentManager;
private ArrayList<Content> friendsContent;

private Login l;
private User user;
    public NewsFeedWindow() {
        initComponents();
        model = new DefaultComboBoxModel<>();
        listModel = new DefaultListModel<>();
        list = new JList<>(listModel);
          l=new Login();
         user=l.sendUser();
        accountManagement=new UserDatabaseManagement() ;
        friendManager=new ManageFriends();
        contentManager=new ContentManagement();
        friendsContent=new ArrayList<>();
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


        updateFriends();
        displayContents();
        
        // update();
 
    }


     
   
        public void updateFriends()
     {
      for (User u:friendManager.getFriends())
      {
          friendsContent.add(contentManager.getContent(u.getId()));
      }
     }

     public void updateNewsfeed()

     {
      for (User u:friendManager.getFriends())
      {
          friendsContent.add(contentManager.getContent(u.getId()));
      }
     }
     
     void displayContents()
     {
          postsPanel.removeAll(); // Clear previous content

        for (Content content : friendsContent) {
            if (content.isStory() && content.isExpired()) {
                continue; // Skip expired stories
            }

            // Create a panel for each post or story
            JPanel contentPanel = new JPanel();
            contentPanel.setLayout(new BorderLayout());
            contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            contentPanel.setBackground(Color.LIGHT_GRAY);

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

        postsPanel.revalidate();
        postsPanel.repaint();
     }
     
 
     public void update(){
        model.removeAllElements();
        listModel.clear();
        if(friendManager.getFriends()!=null){
        for(User friend:friendManager.getFriends()){
            String username=friend.getUsername();
            String status=friend.getStatus();
            String displayedText=username + " (" + status + ")";
             model.addElement(displayedText);
            listModel.addElement(friend.getUsername());}}
        else JOptionPane.showMessageDialog(null, 
            ("no friends feed is empty"), 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
         revalidate();
         repaint();
        
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
        jScrollPane1 = new javax.swing.JScrollPane();
        friendList = new javax.swing.JList<>();
        SelectFriend = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        Remove = new javax.swing.JButton();
        Block = new javax.swing.JButton();
        addFriend = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 657, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 452, Short.MAX_VALUE)
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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(addPostBtn)
                .addGap(34, 34, 34)
                .addComponent(addStoryBtn)
                .addGap(28, 28, 28)
                .addComponent(friendSuggestion)
                .addGap(48, 48, 48)
                .addComponent(friendRequest)
                .addGap(37, 37, 37)
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
                    .addComponent(logoutBtn))
                .addGap(15, 15, 15))
        );

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

        Remove.setText("Remove");
        Remove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RemoveActionPerformed(evt);
            }
        });

        Block.setText("Block ");
        Block.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BlockActionPerformed(evt);
            }
        });

        addFriend.setText("Add Friend");
        addFriend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addFriendActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(Remove)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(Block))
                            .addComponent(addFriend, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(39, 39, 39))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(SelectFriend, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(SelectFriend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Block, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(Remove, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7)
                        .addComponent(addFriend)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SelectFriendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SelectFriendActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SelectFriendActionPerformed

    private void RemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RemoveActionPerformed
        String username = (String) SelectFriend.getSelectedItem();
        if (username.equals("Search by username")) {
            JOptionPane.showMessageDialog(this, "Please select a user first.");
        } else {
            for(User friend:friendManager.getFriends())
            if(username.equals(friend.getUsername())){
                friendManager.RemoveFriend(friend);
                updateFriends();
                update();
            }
        }
    }//GEN-LAST:event_RemoveActionPerformed

    private void BlockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BlockActionPerformed
        String username = (String) SelectFriend.getSelectedItem();
        if (username.equals("Search by username")) {
            JOptionPane.showMessageDialog(this, "Please select a user first.");
        } else {
            for(User friend:friendManager.getFriends())
            if(username.equals(friend.getUsername())){
                friendManager.BlockFriend(friend);
              updateFriends();
              update();
            
            }}
    }//GEN-LAST:event_BlockActionPerformed

    private void addFriendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addFriendActionPerformed
        String username = JOptionPane.showInputDialog("Search");
        for(User user:accountManagement.loadUsers())
        if(username.equals(user.getUsername()))
        friendManager.AddFriend(user);
    }//GEN-LAST:event_addFriendActionPerformed

    private void friendSuggestionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_friendSuggestionActionPerformed
       FriendSuggestionsWindow friendSuggestionWindow=new FriendSuggestionsWindow();
       friendSuggestionWindow.setVisible(true);
       this.setVisible(false);
    }//GEN-LAST:event_friendSuggestionActionPerformed

    private void friendRequestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_friendRequestActionPerformed
        FriendRequestWindow friendRequestWindow = new  FriendRequestWindow();
       friendRequestWindow.setVisible(true);
       this.setVisible(false);
    }//GEN-LAST:event_friendRequestActionPerformed

    private void addStoryBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addStoryBtnActionPerformed
        // TODO add your handling code here:
        AddStoryWindow w=new AddStoryWindow(this,true);
        w.setVisible(true);
    }//GEN-LAST:event_addStoryBtnActionPerformed

    private void addPostBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addPostBtnActionPerformed
        // TODO add your handling code here:
        AddPostWindow w=new AddPostWindow(this,true);
        w.setVisible(true);
    }//GEN-LAST:event_addPostBtnActionPerformed

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
                new NewsFeedWindow().setVisible(true);
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton logoutBtn;
    // End of variables declaration//GEN-END:variables
}



