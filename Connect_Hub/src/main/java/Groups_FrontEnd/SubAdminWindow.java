/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Groups_FrontEnd;

import Content_Creation.Backend.Post;
import Groups_Backend.Admin;
import Groups_Backend.CurrentGroup;
import Groups_Backend.Group;
import Groups_Backend.GroupManager;
import Groups_Backend.SubAdmin;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import userdatabasemanagement.CurrentUser;
import userdatabasemanagement.User;
import userdatabasemanagement.UserDatabaseManagement;

/**
 *
 * @author sherrygirguis
 */
public class SubAdminWindow extends javax.swing.JFrame {


        private ArrayList<Post> groupPosts;

     private JPanel postsPanel;
    private JScrollPane scrollPane;
    private GroupManager manager;
    private User thisUser;
    private Group thisGroup;
    private UserDatabaseManagement accountManager;
    private SubAdmin subAdmin;

    /**
     * Creates new form SubAdminWindow
     */
    public SubAdminWindow() {
        initComponents();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        manager = new GroupManager();
        thisUser = CurrentUser.getInstance().getCurrentUser();
        thisGroup = CurrentGroup.getInstance().getCurrentGroup();
        accountManager = new UserDatabaseManagement();
        update();
        if (manager.isSAdmin(thisUser, thisGroup)) {
            thisUser = new SubAdmin();
            if (thisUser instanceof Admin) {
                // If the user is now an Admin, they have permission to remove users
                subAdmin = (SubAdmin) thisUser;
            }
        }
        
        
            groupPosts = new ArrayList<>();

    // Initialize posts panel
    postsPanel = new JPanel();
    postsPanel.setLayout(new BoxLayout(postsPanel, BoxLayout.Y_AXIS)); // Vertical stacking
    postsPanel.setBackground(Color.WHITE);

    // Add posts panel to a scroll pane
    scrollPane = new JScrollPane(postsPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    // Configure the scroll pane
    scrollPane.setPreferredSize(new Dimension(750, 550));
    scrollPane.setBorder(BorderFactory.createEmptyBorder());

    // Configure jPanel2 to hold the scroll pane
    jPanel2.setBackground(Color.WHITE);
    jPanel2.setLayout(new BorderLayout());
    jPanel2.add(scrollPane, BorderLayout.CENTER);

    // Check if user is a super admin
  
    

    // Display posts
    displayContents();

    // Revalidate and repaint after adding all components
    jPanel2.revalidate();
    jPanel2.repaint();
        

    }

    public void update() {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (User user : thisGroup.getMembers()) {
            listModel.addElement(user.getUsername());
        }
        membersList.setModel(listModel);
    }
    
    
    
    
    
public void displayContents() {
    manager.load(); // Load all groups

    // Fetch posts for the current group
    for (Group g : manager.getGroups()) {
        if (thisGroup.getGroupId().equals(g.getGroupId())) {
            groupPosts = g.getPosts();
            break; // Exit the loop once the group is found
        }
    }

    if (groupPosts == null || groupPosts.isEmpty()) {
        System.out.println("No posts to display.");
        return; // Exit if no posts are available
    }

    // Clear previous content
    postsPanel.removeAll();
    postsPanel.revalidate();
    postsPanel.repaint();

    // Prepare the panel for displaying posts
    JPanel postsDisplayPanel = new JPanel();
    postsDisplayPanel.setLayout(new BoxLayout(postsDisplayPanel, BoxLayout.Y_AXIS));
    postsDisplayPanel.setBorder(BorderFactory.createTitledBorder("Posts"));

    // Iterate over posts to display
    for (Post p : groupPosts) {
        if (p == null) {
            System.out.println("Null post found, skipping...");
            continue;
        }

        System.out.println("Displaying post: " + p);

        // Create a panel for the post
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.setBackground(Color.LIGHT_GRAY);

        // Add author and timestamp
        User author = accountManager.getUser(p.getAuthorId());
        if (author != null) {
            JLabel authorLabel = new JLabel("Author: " + author.getUsername() + " | Time: " + p.getTimestamp());
            contentPanel.add(authorLabel, BorderLayout.NORTH);
        } else {
            System.out.println("Author not found for post: " + p.getContentId());
        }

        // Add text content
        if (p.getContent() != null && !p.getContent().isEmpty()) {
            JTextArea contentArea = new JTextArea(p.getContent());
            contentArea.setLineWrap(true);
            contentArea.setWrapStyleWord(true);
            contentArea.setEditable(false);
            contentPanel.add(new JScrollPane(contentArea), BorderLayout.CENTER);
        }

        // Add image if available
        if (p.getImagePath() != null && !p.getImagePath().isEmpty()) {
            try {
                ImageIcon imageIcon = new ImageIcon(p.getImagePath());
                JLabel imageLabel = new JLabel(imageIcon);
                contentPanel.add(imageLabel, BorderLayout.SOUTH);
            } catch (Exception e) {
                System.err.println("Error loading image for post: " + p.getContentId());
                e.printStackTrace();
            }
        }

        // Add the content panel to the display panel
        postsDisplayPanel.add(contentPanel);
    }

    // Add the posts display panel to the main posts panel
    postsPanel.setLayout(new BorderLayout());
    postsPanel.add(postsDisplayPanel, BorderLayout.CENTER);

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
        GroupRequests = new javax.swing.JButton();
        RemoveMember = new javax.swing.JButton();
        PostManager = new javax.swing.JButton();
        LeaveGroup = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        CreatePost = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        membersList = new javax.swing.JList<>();
        jPanel2 = new javax.swing.JPanel();
        SelectMember = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        GroupRequests.setText("Group Requests");
        GroupRequests.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GroupRequestsActionPerformed(evt);
            }
        });

        RemoveMember.setText("Remove Member");
        RemoveMember.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RemoveMemberActionPerformed(evt);
            }
        });

        PostManager.setText("Post Manager");
        PostManager.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PostManagerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(GroupRequests, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(RemoveMember)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PostManager)
                .addContainerGap(447, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(GroupRequests)
                    .addComponent(RemoveMember)
                    .addComponent(PostManager))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        LeaveGroup.setText("Leave Group");
        LeaveGroup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LeaveGroupActionPerformed(evt);
            }
        });

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Members");

        CreatePost.setText("Create Post");
        CreatePost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreatePostActionPerformed(evt);
            }
        });

        membersList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(membersList);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        SelectMember.setText("Select Member");
        SelectMember.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SelectMemberActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(25, 25, 25))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(SelectMember, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(LeaveGroup, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                                    .addComponent(CreatePost, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(14, 14, 14))))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LeaveGroup)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CreatePost))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SelectMember)
                .addContainerGap(112, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CreatePostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreatePostActionPerformed
        // TODO add your handling code here:
        AddGroupPost w=new AddGroupPost(this,true);
        w.setVisible(true);

    }//GEN-LAST:event_CreatePostActionPerformed

    private void GroupRequestsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GroupRequestsActionPerformed
        // TODO add your handling code here:
        RequestWindow requestWindow = new RequestWindow();
        requestWindow.setVisible(true);
    }//GEN-LAST:event_GroupRequestsActionPerformed

    private void RemoveMemberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RemoveMemberActionPerformed
      String selectedMemberUsername =membersList.getSelectedValue();
        if (selectedMemberUsername == null) {
            JOptionPane.showMessageDialog(this, "Select a user first", "Error", JOptionPane.INFORMATION_MESSAGE);
        } else {
            User selectedMember = accountManager.getUserByUsername(selectedMemberUsername);
        
           if (manager.isMember( selectedMember, thisGroup)) {
                subAdmin.removeUser(selectedMember);
                update();
                
            }}

            
    }//GEN-LAST:event_RemoveMemberActionPerformed

    private void PostManagerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PostManagerActionPerformed
        // TODO add your handling code here:
        PostManagerWindow postManagerWindow = new PostManagerWindow();
        postManagerWindow.setVisible(true);
        //mirolla
    }//GEN-LAST:event_PostManagerActionPerformed

    private void SelectMemberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SelectMemberActionPerformed
        // TODO add your handling code here:
         String selectedMemberUsername =membersList.getSelectedValue();
        if (selectedMemberUsername == null) {
            JOptionPane.showMessageDialog(this, "Select a group first", "Error", JOptionPane.INFORMATION_MESSAGE);
        } else {
            User selectedMember = accountManager.getUserByUsername(selectedMemberUsername);
        }
    }//GEN-LAST:event_SelectMemberActionPerformed

    private void LeaveGroupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LeaveGroupActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_LeaveGroupActionPerformed

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
            java.util.logging.Logger.getLogger(SubAdminWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SubAdminWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SubAdminWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SubAdminWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SubAdminWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CreatePost;
    private javax.swing.JButton GroupRequests;
    private javax.swing.JButton LeaveGroup;
    private javax.swing.JButton PostManager;
    private javax.swing.JButton RemoveMember;
    private javax.swing.JButton SelectMember;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> membersList;
    // End of variables declaration//GEN-END:variables
}
