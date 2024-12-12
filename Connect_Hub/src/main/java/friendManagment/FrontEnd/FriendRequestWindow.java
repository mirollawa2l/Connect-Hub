
package friendManagment.FrontEnd;


import Notifications.NotificationWindow;
import friendManagment.Backend.FriendRequest;
import friendManagment.Backend.ManageFriendRequests;
import userdatabasemanagement.UserDatabaseManagement;
import userdatabasemanagement.User;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import userdatabasemanagement.CurrentUser;

public class FriendRequestWindow extends javax.swing.JFrame {
private  DefaultComboBoxModel<String> model ;
private DefaultListModel<String> listModel ;
private ManageFriendRequests requestManager=new  ManageFriendRequests();
private UserDatabaseManagement accountManagement=new UserDatabaseManagement() ;
 private User thisUser= CurrentUser.getInstance().getCurrentUser();
boolean close=false;
private NotificationWindow notificationWindow;

//connecting the account manager and friendManager to the windows

    public FriendRequestWindow() {
        initComponents();
        setDefaultCloseOperation( FriendRequestWindow.DISPOSE_ON_CLOSE);
        
        model = new DefaultComboBoxModel<>();
         SelectUser.setModel(model);
        listModel = new DefaultListModel<>();
         jList1.setModel(listModel);
        jList1 = new JList<>(listModel);
       // notificationWindow = new NotificationWindow();
        //notificationWindow.setVisible(true);
         update();
    }
    public void update(){
       
        model.removeAllElements();
        listModel.clear();
        model.addElement("Choose Request");
        for(FriendRequest request:requestManager.loadFriendRequests(thisUser.getId())){
             model.addElement(request.getSender().getUsername());
             listModel.addElement(request.getSender().getUsername());}

    }
        
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        SelectUser = new javax.swing.JComboBox<>();
        Decline = new javax.swing.JButton();
        Accept = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        sendFriendRequest = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        SelectUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SelectUserActionPerformed(evt);
            }
        });

        Decline.setText("Decline");
        Decline.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeclineActionPerformed(evt);
            }
        });

        Accept.setText("Accept");
        Accept.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AcceptActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(jList1);

        jLabel1.setText("Friend Requests");

        sendFriendRequest.setText("Send Friend Request");
        sendFriendRequest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendFriendRequestActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sendFriendRequest)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Decline)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Accept))
                    .addComponent(SelectUser, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(SelectUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Decline)
                            .addComponent(Accept))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sendFriendRequest))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addGap(5, 5, 5)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SelectUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SelectUserActionPerformed

    }//GEN-LAST:event_SelectUserActionPerformed

    private void DeclineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeclineActionPerformed
        String username = (String) SelectUser.getSelectedItem();
        if (username.equals("Choose Request")) {
            JOptionPane.showMessageDialog(this, "Please select a user first.");
        } else {
            if(requestManager.getRequest(username)!=null){
            FriendRequest requestToDecline=requestManager.getRequest(username);
            requestManager.declineRequest(requestToDecline);
            //notificationWindow.loadNotifications();
            update(); }
            else JOptionPane.showMessageDialog(null,"No user found!","Error",JOptionPane.INFORMATION_MESSAGE); }   
    }//GEN-LAST:event_DeclineActionPerformed

    private void AcceptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AcceptActionPerformed
        String username = (String) SelectUser.getSelectedItem();
        if (username.equals("Choose Request")) {
            JOptionPane.showMessageDialog(this, "Please select a request first.");
        } else {
            if(requestManager.getRequest(username)!=null){
            FriendRequest requestToAccept=requestManager.getRequest(username);
            requestManager.acceptRequest(requestToAccept);
            //notificationWindow.loadNotifications();
            update();}
            else JOptionPane.showMessageDialog(null,"No user found!","Error",JOptionPane.INFORMATION_MESSAGE); }   


    }//GEN-LAST:event_AcceptActionPerformed

    private void sendFriendRequestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendFriendRequestActionPerformed
                boolean found=false;
        String username = JOptionPane.showInputDialog("Search");

           if (username == null || username.trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter a username to search.");
        return;}
          for(User user:accountManagement.loadUsers()){

            
            if(username.equals(user.getUsername())){
               requestManager.sendRequest(thisUser,user);
              // notificationWindow.loadNotifications();
                found=true;

               System.out. print("found");
            break;
            }
          }

         if(!found)
           JOptionPane.showMessageDialog(null,"No user found!","Error",JOptionPane.INFORMATION_MESSAGE);  
    }//GEN-LAST:event_sendFriendRequestActionPerformed


    
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
            java.util.logging.Logger.getLogger(FriendRequestWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FriendRequestWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FriendRequestWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FriendRequestWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FriendRequestWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Accept;
    private javax.swing.JButton Decline;
    private javax.swing.JComboBox<String> SelectUser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList<String> jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton sendFriendRequest;
    // End of variables declaration//GEN-END:variables
}
