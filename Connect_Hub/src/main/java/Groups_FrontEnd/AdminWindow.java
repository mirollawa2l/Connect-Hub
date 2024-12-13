/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Groups_FrontEnd;

import Content_Creation.Backend.Content;
import Content_Creation.Backend.Post;
import Groups_Backend.Admin;
import Groups_Backend.CurrentGroup;
import Groups_Backend.Group;
import Groups_Backend.GroupManager;
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
public class AdminWindow extends javax.swing.JFrame {

    private JPanel postsPanel;
    private JScrollPane scrollPane;
    private GroupManager manager;
    private User thisUser;
    private Group thisGroup;
    private UserDatabaseManagement accountManager;
    private Admin admin;
    private ArrayList<Post> groupPosts;
    private UserDatabaseManagement accountManagement;

    /**
     * Creates new form AdminWindoe
     */
    public AdminWindow() {
        initComponents();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        accountManagement = new UserDatabaseManagement();

        manager = new GroupManager();
        thisUser = CurrentUser.getInstance().getCurrentUser();
        thisGroup = CurrentGroup.getInstance().getCurrentGroup();
        accountManager = new UserDatabaseManagement();
        groupPosts = new ArrayList<>();
        updateList();
        postsPanel = new JPanel();
        postsPanel.setLayout(new BoxLayout(postsPanel, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(postsPanel);
        postsPanel.setPreferredSize(new Dimension(1200, 1200));
        postsPanel.setBackground(Color.WHITE);
        if (manager.isSAdmin(thisUser, thisGroup)) {
            thisUser = new Admin();
            if (thisUser instanceof Admin) {
                // If the user is now an Admin, they have permission to remove users
                admin = (Admin) thisUser;
            }
        }
        displayContents();

    }

    public void updateList() {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (User user : thisGroup.getMembers()) {
            listModel.addElement(user.getUsername());
        }
        membersList.setModel(listModel);
    }

    public void displayContents() {
        manager.load();
        groupPosts = manager.getGroup(thisGroup.getGroupId()).getPosts();
        postsPanel.removeAll(); // Clear previous content
        postsPanel.revalidate();
        postsPanel.repaint();

        JPanel postsDisplayPanel = new JPanel();
        postsDisplayPanel.setLayout(new BoxLayout(postsDisplayPanel, BoxLayout.Y_AXIS));
        postsDisplayPanel.setBorder(BorderFactory.createTitledBorder("Posts"));

        for (Post post : groupPosts) {
            if (post == null) {
                System.out.println("Null content found, skipping...");
                continue;
            }
            System.out.println("Displaying content: " + post);

            JPanel contentPanel = new JPanel(new BorderLayout());
            contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            contentPanel.setBackground(Color.LIGHT_GRAY);
            JLabel authorLabel = new JLabel("Author: " + accountManagement.getUser(post.getAuthorId()).getUsername() + " Time: " + post.getTimestamp());
            contentPanel.add(authorLabel, BorderLayout.NORTH);

            // Display text content if available
            if (post.getContent() != null && !post.getContent().isEmpty()) {
                JTextArea contentArea = new JTextArea(post.getContent());
                contentArea.setLineWrap(true);
                contentArea.setWrapStyleWord(true);
                contentArea.setEditable(false); // Make the content non-editable
                contentPanel.add(new JScrollPane(contentArea), BorderLayout.CENTER);
            }

            // Display image if available
            if (post.getImagePath() != null && !post.getImagePath().isEmpty()) {
                try {
                    ImageIcon imageIcon = new ImageIcon(post.getImagePath()); // Load image from path
                    JLabel imageLabel = new JLabel();
                    imageLabel.setIcon(imageIcon);
                    contentPanel.add(imageLabel, BorderLayout.SOUTH);
                } catch (Exception e) {
                    System.err.println("Error loading image for content: " + post.getContentId());
                    e.printStackTrace();
                }
            }

        }

        // Combine stories and posts panels
        JPanel combinedPanel = new JPanel(new BorderLayout());
        combinedPanel.add(postsDisplayPanel, BorderLayout.CENTER);

        // Add combined panel to the main postsPanel
        postsPanel.setLayout(new BorderLayout());
        postsPanel.add(combinedPanel, BorderLayout.CENTER);

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
        jPanel3 = new javax.swing.JPanel();
        GroupRequests = new javax.swing.JButton();
        RemoveMember = new javax.swing.JButton();
        DemoteMember = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        membersList = new javax.swing.JList<>();
        jLabel2 = new javax.swing.JLabel();
        SelectMember = new javax.swing.JButton();
        Refresh = new javax.swing.JButton();
        PostManager = new javax.swing.JButton();
        DeleteGroup = new javax.swing.JButton();
        PromoteMember = new javax.swing.JButton();

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

        DemoteMember.setText("Demote Member");
        DemoteMember.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DemoteMemberActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(GroupRequests)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(RemoveMember)
                .addGap(18, 18, 18)
                .addComponent(DemoteMember)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DemoteMember)
                    .addComponent(RemoveMember)
                    .addComponent(GroupRequests))
                .addGap(0, 41, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        membersList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(membersList);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Members");

        SelectMember.setText("Select Member");
        SelectMember.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SelectMemberActionPerformed(evt);
            }
        });

        Refresh.setText("Refresh");
        Refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefreshActionPerformed(evt);
            }
        });

        PostManager.setText("Post Manager");
        PostManager.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PostManagerActionPerformed(evt);
            }
        });

        DeleteGroup.setText("Delete Group");
        DeleteGroup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteGroupActionPerformed(evt);
            }
        });

        PromoteMember.setText("Promote Member");
        PromoteMember.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PromoteMemberActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(PromoteMember)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(PostManager, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(DeleteGroup)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(SelectMember, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(Refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(23, 23, 23))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(PromoteMember)
                            .addComponent(PostManager)
                            .addComponent(DeleteGroup))
                        .addGap(35, 35, 35)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(SelectMember)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Refresh)
                .addGap(22, 22, 22))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void GroupRequestsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GroupRequestsActionPerformed
        // TODO add your handling code here:
        RequestWindow requestWindow = new RequestWindow();
        requestWindow.setVisible(true);
    }//GEN-LAST:event_GroupRequestsActionPerformed

    private void RemoveMemberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RemoveMemberActionPerformed
        // TODO add your handling code here:
        boolean found = false;
        String userName = JOptionPane.showInputDialog("Choose");

        if (userName == null || userName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a group");
        } else {
            if (manager.isMember(accountManager.getUserByUsername(userName), thisGroup)) {
                admin.removeUser(accountManager.getUserByUsername(userName));
                found = true;
                updateList();
                System.out.print("found");
            }

            if (!found) {
                JOptionPane.showMessageDialog(null, "No Group found!", "Error", JOptionPane.INFORMATION_MESSAGE);
            }
        }


    }//GEN-LAST:event_RemoveMemberActionPerformed

    private void PostManagerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PostManagerActionPerformed
        // TODO add your handling code here:
        PostManagerWindow postManagerWindow = new PostManagerWindow();
        postManagerWindow.setVisible(true);
        //mirolla
    }//GEN-LAST:event_PostManagerActionPerformed

    private void DeleteGroupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteGroupActionPerformed
        // TODO add your handling code here:
        admin.deleteGroup(thisGroup);

        JOptionPane.showMessageDialog(this, "Group deleted succeseefully");
        this.setVisible(false);

    }//GEN-LAST:event_DeleteGroupActionPerformed

    private void DemoteMemberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DemoteMemberActionPerformed
        // TODO add your handling code here:
        boolean found = false;
        String userName = JOptionPane.showInputDialog("Choose");

        if (userName == null || userName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a user first");
        } else {
            if (manager.isSubAdmin(accountManager.getUserByUsername(userName), thisGroup)) {
                admin.demoteMember(accountManager.getUserByUsername(userName));
                found = true;
                updateList();
                System.out.print("found");
            } else {
                JOptionPane.showMessageDialog(null, "Cannot demote a member!", "Error", JOptionPane.INFORMATION_MESSAGE);
            }

            if (!found) {
                JOptionPane.showMessageDialog(null, "No Group found!", "Error", JOptionPane.INFORMATION_MESSAGE);
            }
        }


    }//GEN-LAST:event_DemoteMemberActionPerformed

    private void PromoteMemberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PromoteMemberActionPerformed
        // TODO add your handling code here:
        boolean found = false;
        String userName = JOptionPane.showInputDialog("Choose");

        if (userName == null || userName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a user first");
        } else {
            admin.promoteMember(accountManager.getUserByUsername(userName));
            found = true;
            updateList();
            System.out.print("found");
        }

        if (!found) {
            JOptionPane.showMessageDialog(null, "No Group found!", "Error", JOptionPane.INFORMATION_MESSAGE);
        }


    }//GEN-LAST:event_PromoteMemberActionPerformed

    private void SelectMemberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SelectMemberActionPerformed
        String selectedMemberUsername = membersList.getSelectedValue();
        if (selectedMemberUsername == null) {
            JOptionPane.showMessageDialog(this, "Select a group first", "Error", JOptionPane.INFORMATION_MESSAGE);
        } else {
            User selectedMember = accountManager.getUserByUsername(selectedMemberUsername);
        }
    }//GEN-LAST:event_SelectMemberActionPerformed

    private void RefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshActionPerformed
        // TODO add your handling code here:
        displayContents();
    }//GEN-LAST:event_RefreshActionPerformed

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
            java.util.logging.Logger.getLogger(AdminWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton DeleteGroup;
    private javax.swing.JButton DemoteMember;
    private javax.swing.JButton GroupRequests;
    private javax.swing.JButton PostManager;
    private javax.swing.JButton PromoteMember;
    private javax.swing.JButton Refresh;
    private javax.swing.JButton RemoveMember;
    private javax.swing.JButton SelectMember;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> membersList;
    // End of variables declaration//GEN-END:variables
}
