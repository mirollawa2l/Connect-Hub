/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PostInteraction;

/**
 *
 * @author Yara
 */
import Content_Creation.Backend.Content;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import userdatabasemanagement.UserDatabaseManagement;

public class CommentsWindow extends JFrame {
    private JPanel commentsPanel;
    private JScrollPane scrollPane;
    private Content content;
    private UserDatabaseManagement accountManager;

    public CommentsWindow(Content content) {
        this.content = content;
accountManager=new UserDatabaseManagement();
        // Set up window properties
        setTitle("Comments for Post: " + content.getContentId());
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Initialize components
        commentsPanel = new JPanel();
        commentsPanel.setLayout(new BoxLayout(commentsPanel, BoxLayout.Y_AXIS));

        scrollPane = new JScrollPane(commentsPanel);
        add(scrollPane, BorderLayout.CENTER);

        displayComments();

        setVisible(true);
    }

    private void displayComments() {
        commentsPanel.removeAll();

        List<Comment> comments = content.getComments(); // Assuming Content has a getComments() method
        if (comments == null || comments.isEmpty()) {
            JLabel noCommentsLabel = new JLabel("No comments yet.");
            noCommentsLabel.setHorizontalAlignment(SwingConstants.CENTER);
            commentsPanel.add(noCommentsLabel);
        } else {
            for (Comment comment : comments) {
                JPanel commentPanel = new JPanel(new BorderLayout());
                commentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                commentPanel.setBackground(Color.LIGHT_GRAY);

                JLabel authorLabel = new JLabel("Author: " + accountManager.getUser(comment.getAuthorId()).getUsername() + " | Time: " + comment.getTimestamp());
                JTextArea commentText = new JTextArea(comment.getText());
                commentText.setLineWrap(true);
                commentText.setWrapStyleWord(true);
                commentText.setEditable(false);

                commentPanel.add(authorLabel, BorderLayout.NORTH);
                commentPanel.add(new JScrollPane(commentText), BorderLayout.CENTER);

                commentsPanel.add(commentPanel);
            }
        }

        commentsPanel.revalidate();
        commentsPanel.repaint();
    }
}

