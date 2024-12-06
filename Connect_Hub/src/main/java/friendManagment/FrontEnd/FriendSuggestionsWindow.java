
package friendManagment.FrontEnd;


import friendManagment.Backend.FriendSuggestion;
import friendManagment.Backend.FriendSuggestionManager;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;


public class FriendSuggestionsWindow extends javax.swing.JFrame {
private  DefaultComboBoxModel<String> model ;
private DefaultListModel<String> listModel ;
private JList<String> list;
private FriendSuggestionManager suggestionManager;

 
    public FriendSuggestionsWindow() {
        initComponents();
        model = new DefaultComboBoxModel<>();
        listModel = new DefaultListModel<>();
        list = new JList<>(listModel);
         suggestionManager=new FriendSuggestionManager();
         model.removeAllElements();
        listModel.clear();
         JComboBox<String> comboBox = new JComboBox<>(model);
         for(FriendSuggestion suggestion:suggestionManager.generateSuggestions()){
             model.addElement(suggestion.getSuggested().getUsername());
             listModel.addElement(suggestion.getSuggested().getUsername());}
         
        
    }
    
    public void update(){
        model.removeAllElements();
        listModel.clear();
        
        model.addElement("Choose Suggestion");
        for(FriendSuggestion suggestion:suggestionManager.getListOfSuggestions()){
             model.addElement(suggestion.getSuggested().getUsername());
             listModel.addElement(suggestion.getSuggested().getUsername());}
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

        jLabel1.setText("Friend Suggestions");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(Decline)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Accept))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(SelectUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(SelectUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Decline)
                    .addComponent(Accept))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(5, 5, 5)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void DeclineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeclineActionPerformed
       String username = (String) SelectUser.getSelectedItem();
        if (username.equals("Choose Suggestion")) {
            JOptionPane.showMessageDialog(this, "Please select a user first.");
        } else {
            FriendSuggestion suggestionToDecline=suggestionManager.getSuggestion(username);
             suggestionManager.acceptFriendSuggestion(suggestionToDecline);
             update();
        
        }
    }//GEN-LAST:event_DeclineActionPerformed

    private void SelectUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SelectUserActionPerformed
        
    }//GEN-LAST:event_SelectUserActionPerformed

    private void AcceptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AcceptActionPerformed
        String username = (String) SelectUser.getSelectedItem();
        if (username.equals("Choose Suggestion")) {
            JOptionPane.showMessageDialog(this, "Please select a user first.");
        } else {
            FriendSuggestion suggestionToAccept=suggestionManager.getSuggestion(username);
             suggestionManager.acceptFriendSuggestion(suggestionToAccept);
             update();}
        
    }//GEN-LAST:event_AcceptActionPerformed

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
            java.util.logging.Logger.getLogger(FriendSuggestionsWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FriendSuggestionsWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FriendSuggestionsWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FriendSuggestionsWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FriendSuggestionsWindow().setVisible(true);
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
    // End of variables declaration//GEN-END:variables
}
