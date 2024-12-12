package friendManagment.FrontEnd;



import friendManagment.Backend.ManageFriends;
import java.util.ArrayList;
import userdatabasemanagement.UserDatabaseManagement;
import userdatabasemanagement.User;
import javax.swing.DefaultComboBoxModel;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import userdatabasemanagement.CurrentUser;



public class FriendListWindow extends javax.swing.JFrame {

private DefaultComboBoxModel<String> model;
    private DefaultListModel<String> listModel;
    private ManageFriends friendManager;
    private User user;
     private UserDatabaseManagement accountManagement;
    

    public FriendListWindow() {
        initComponents();
         setDefaultCloseOperation( FriendSuggestionsWindow.DISPOSE_ON_CLOSE);
        user = CurrentUser.getInstance().getCurrentUser();
        model = new DefaultComboBoxModel<>();
        SelectFriend.setModel(model);
        listModel = new DefaultListModel<>();
        friendList.setModel(listModel);
        friendList = new JList<>(listModel);
        accountManagement = new UserDatabaseManagement();
        friendManager = new ManageFriends();
        update();
    }
    public void update() {
        model.removeAllElements();
        listModel.clear();
        model.addElement("Search");
        ArrayList<User> friends = friendManager.loadFriends(user.getId());
        for (User friend : friends) {
            System.out.print(friend.getUsername());
        }
        if (friends != null) {
            for (User friend : friends) {
                String username = friend.getUsername();
                String status = friend.getStatus();
                String displayedText = username + " (" + status + ")";
                model.addElement(friend.getUsername());
                listModel.addElement(displayedText);
            }

        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        friendList = new javax.swing.JList<>();
        jLabel2 = new javax.swing.JLabel();
        Block = new javax.swing.JButton();
        Remove = new javax.swing.JButton();
        SelectFriend = new javax.swing.JComboBox<>();
        addFriend = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        friendList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(friendList);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Friend List ");

        Block.setText("Block ");
        Block.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BlockActionPerformed(evt);
            }
        });

        Remove.setText("Remove");
        Remove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RemoveActionPerformed(evt);
            }
        });

        SelectFriend.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(addFriend, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(6, 6, 6)
                                    .addComponent(Remove)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(Block))))))
                .addGap(18, 18, 18)
                .addComponent(SelectFriend, 0, 108, Short.MAX_VALUE)
                .addGap(37, 37, 37))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Remove)
                            .addComponent(Block))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                        .addComponent(addFriend))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(SelectFriend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BlockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BlockActionPerformed
       boolean found = false;
        String username = (String) SelectFriend.getSelectedItem();
        if (username.equals("Search")) {
            JOptionPane.showMessageDialog(this, "Please select a user first.");
        } else {

            if (accountManagement.loadUsers() != null) {
                for (User user : accountManagement.loadUsers()) {
                    if (username.equals(user.getUsername())) {
                        friendManager.BlockFriend(user);
                        update();
                        found = true;
                        JOptionPane.showMessageDialog(null, "user blocked sucessfully!", "Sucess", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Id not found!", "Error", JOptionPane.INFORMATION_MESSAGE);
            }
            if (!found) {
                JOptionPane.showMessageDialog(null, "No user found!", "Error", JOptionPane.INFORMATION_MESSAGE);
            }
        }

    }//GEN-LAST:event_BlockActionPerformed

    private void RemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RemoveActionPerformed
       boolean found = false;
        String username = (String) SelectFriend.getSelectedItem();
        if (username.equals("Search")) {
            JOptionPane.showMessageDialog(this, "Please select a user first.");
        } else {
            if (friendManager.getFriends() != null) {
                for (User friend : friendManager.getFriends()) {
                    if (username.equals(friend.getUsername())) {
                        friendManager.RemoveFriend(friend);
                        update();
                        
                        found = true;
                        break;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "User has no friends!", "Error", JOptionPane.INFORMATION_MESSAGE);
            }
            if (!found) {
                JOptionPane.showMessageDialog(null, "No user found!", "Error", JOptionPane.INFORMATION_MESSAGE);
            }
        }
           
    }//GEN-LAST:event_RemoveActionPerformed

    private void addFriendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addFriendActionPerformed
         boolean found = false;
        String username = JOptionPane.showInputDialog("Search");

        if (username == null || username.trim().isEmpty()) {

        JOptionPane.showMessageDialog(this, "Please enter a username to search.");
        return;
    }
        
        else {
            for (User u : accountManagement.loadUsers()) {
                if (username.trim().equalsIgnoreCase(u.getUsername())) {
                    friendManager.AddFriend(u);
                    update();
                 
                    System.out.println("found");
                    found = true;
                    break;
                }
            }
            if (!found) {
                JOptionPane.showMessageDialog(null, "No user found!", "Error", JOptionPane.INFORMATION_MESSAGE);
            }
        }                                       

    }//GEN-LAST:event_addFriendActionPerformed

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
            java.util.logging.Logger.getLogger(FriendListWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FriendListWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FriendListWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FriendListWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FriendListWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Block;
    private javax.swing.JButton Remove;
    private javax.swing.JComboBox<String> SelectFriend;
    private javax.swing.JButton addFriend;
    private javax.swing.JList<String> friendList;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
