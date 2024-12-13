package Groups_FrontEnd;

import Groups_Backend.Group;
import Groups_Backend.GroupManager;
import Content_Creation.Backend.Post;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import userdatabasemanagement.UserDatabaseManagement;


public class PostWindow extends JFrame {
    private UserDatabaseManagement accountManager;

    private GroupManager manager;
    private Group currentGroup;

    public PostWindow(GroupManager manager, Group group) {
        accountManager=new UserDatabaseManagement();
        this.manager = manager;
        this.currentGroup = group;
        initComponents();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initComponents() {
        JPanel jPanel1 = new JPanel();
        JButton deletePostButton = new JButton();
        JButton editPostButton = new JButton();
        JButton addPostButton = new JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        deletePostButton.setText("Delete Post");
        deletePostButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                deletePostActionPerformed(evt);
            }
        });

        editPostButton.setText("Edit Post");
        editPostButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                editPostActionPerformed(evt);
            }
        });

        addPostButton.setText("Add Post");
        addPostButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                addPostActionPerformed(evt);
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(deletePostButton)
                    .addComponent(editPostButton)
                    .addComponent(addPostButton))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addComponent(deletePostButton)
                .addGap(34, 34, 34)
                .addComponent(editPostButton)
                .addGap(36, 36, 36)
                .addComponent(addPostButton)
                .addContainerGap(89, Short.MAX_VALUE))
        );

        pack();
    }

    private void addPostActionPerformed(ActionEvent evt) {
     AddGroupPost w=new AddGroupPost(this,true);
w.setVisible(true);
    }

    private void editPostActionPerformed(ActionEvent evt) {
        String author = JOptionPane.showInputDialog(this, "Enter author's name:", "Edit Post", JOptionPane.PLAIN_MESSAGE);
        String time = JOptionPane.showInputDialog(this, "Enter post time (yyyy-MM-dd HH:mm):", "Edit Post", JOptionPane.PLAIN_MESSAGE);

        if (author != null && time != null) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                java.time.LocalDateTime postTime = java.time.LocalDateTime.parse(time, formatter);

                for (Post post : currentGroup.getPosts()) {
                    String name=accountManager.getUser(post.getAuthorId()).getUsername();
                    if (name.equals(author) && post.getTimestamp().isEqual(postTime)) {
                        String newContent = JOptionPane.showInputDialog(this, "Enter new content:", "Edit Post", JOptionPane.PLAIN_MESSAGE);
                        if (newContent != null && !newContent.isEmpty()) {
                            post.setContent(newContent);
                            manager.save();
                            JOptionPane.showMessageDialog(this, "Post edited successfully.");
                            return;
                        }
                    }
                }
                JOptionPane.showMessageDialog(this, "Post not found.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid date format.");
            }
        }
    }

    private void deletePostActionPerformed(ActionEvent evt) {
        String author = JOptionPane.showInputDialog(this, "Enter author's name:", "Delete Post", JOptionPane.PLAIN_MESSAGE);
        String time = JOptionPane.showInputDialog(this, "Enter post time (yyyy-MM-dd HH:mm):", "Delete Post", JOptionPane.PLAIN_MESSAGE);

        if (author != null && time != null) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                java.time.LocalDateTime postTime = java.time.LocalDateTime.parse(time, formatter);

                ArrayList<Post> posts = currentGroup.getPosts();
                for (Post post : posts) {
                    String name = accountManager.getUser(post.getAuthorId()).getUsername();
                    if (name.equals(author) && post.getTimestamp().isEqual(postTime)) {
                        posts.remove(post);
                        manager.save();
                        JOptionPane.showMessageDialog(this, "Post deleted successfully.");
                        return;
                    }
                }
                JOptionPane.showMessageDialog(this, "Post not found.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid date format.");
            }
        }
    }
}
