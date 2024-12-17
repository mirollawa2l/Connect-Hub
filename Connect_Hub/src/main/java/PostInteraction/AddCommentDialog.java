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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import userdatabasemanagement.CurrentUser;

public class AddCommentDialog extends JDialog {

    private JTextArea commentTextArea;
    private JButton submitButton, cancelButton;
    private boolean commentAdded;
    private Comment newComment;

    public AddCommentDialog(Frame parent, boolean modal, Content content) {
        super(parent, modal);
        setTitle("Add Comment");
        setLayout(new BorderLayout());
        setSize(400, 300);
        setLocationRelativeTo(parent);

        // Text area for entering the comment
        commentTextArea = new JTextArea(10, 30);
        commentTextArea.setLineWrap(true);
        commentTextArea.setWrapStyleWord(true);

        // Add components to the dialog
        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        textPanel.add(new JLabel("Write your comment:"), BorderLayout.NORTH);
        textPanel.add(new JScrollPane(commentTextArea), BorderLayout.CENTER);
        add(textPanel, BorderLayout.CENTER);

        // Buttons for submitting or canceling
        JPanel buttonPanel = new JPanel();
        submitButton = new JButton("Submit");
        cancelButton = new JButton("Cancel");

        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action listeners for buttons
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String commentText = commentTextArea.getText().trim();
                if (!commentText.isEmpty()) {
                    // Create a new comment and mark as added
                    newComment = new Comment(CurrentUser.getInstance().getCurrentUser().getId(),
                            commentText, LocalDateTime.now());
                    content.getComments().add(newComment); // Add to the post
                    commentAdded = true;
                    dispose(); // Close the dialog
                } else {
                    JOptionPane.showMessageDialog(AddCommentDialog.this,
                            "Comment cannot be empty!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(e -> dispose());
    }

    // Getter for the new comment
    public Comment getNewComment() {
        return newComment;
    }

    // Check if a comment was successfully added
    public boolean isCommentAdded() {
        return commentAdded;
    }
}
