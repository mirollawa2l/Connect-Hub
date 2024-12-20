package PostInteraction;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import userdatabasemanagement.UserDatabaseManagement;

public class CommentsWindow extends JFrame {
    private JPanel commentsPanel;
    private JScrollPane scrollPane;
    private CommentProvider commentProvider;
    private UserDatabaseManagement accountManager;

    public CommentsWindow(CommentProvider commentProvider) {
        this.commentProvider = commentProvider;
        accountManager = new UserDatabaseManagement();

        // Set up window properties
        setTitle("Comments");
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
        List<Comment> comments = commentProvider.getComments();

        if (comments == null || comments.isEmpty()) {
            JLabel noCommentsLabel = new JLabel("No comments yet.");
            noCommentsLabel.setHorizontalAlignment(SwingConstants.CENTER);
            commentsPanel.add(noCommentsLabel);
        } else {
            for (Comment comment : comments) {
                CommentDecorator decoratedComment = new TimestampedCommentDecorator(comment);

                JPanel commentPanel = new JPanel(new BorderLayout());
                commentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                commentPanel.setBackground(Color.LIGHT_GRAY);

                JLabel authorLabel = new JLabel("Author: " + accountManager.getUser(comment.getAuthorId()).getUsername());
                JTextArea commentText = new JTextArea(decoratedComment.decorate());
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
