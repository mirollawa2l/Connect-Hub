/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package profilemanagement;

/**
 *
 * @author Yara
 */
import javax.swing.*;
import java.awt.*;
import java.util.List;
import Content_Creation.Backend.Post;
import java.io.IOException;
import userdatabasemanagement.User;

public class ViewProfile extends JFrame {

    private User selectedUser;
    private JLabel profilePictureLabel;
    private JLabel coverPictureLabel;

    public ViewProfile(User selectedUser) throws IOException {
        this.selectedUser = selectedUser;
        List<Post> userPosts = new PostRepository().findPostsByUserId(selectedUser.getId());
        initComponents(userPosts);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initComponents(List<Post> userPosts) {
        setTitle("View Profile");
        setSize(900, 700);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Cover Photo Section
        JPanel coverPanel = new JPanel(new BorderLayout());
        coverPanel.setPreferredSize(new Dimension(900, 250));
        if (selectedUser.getCoverPhotoPath() != null && !selectedUser.getCoverPhotoPath().isEmpty()) {
            coverPictureLabel = new JLabel();
            coverPictureLabel.setIcon(resizeImageIcon(selectedUser.getCoverPhotoPath(), 900, 250));
        } else {
            coverPictureLabel = new JLabel("No Cover Photo", SwingConstants.CENTER);
            coverPictureLabel.setFont(new Font("Arial", Font.BOLD, 18));
            coverPictureLabel.setForeground(Color.GRAY);
        }
        coverPanel.add(coverPictureLabel, BorderLayout.CENTER);
        add(coverPanel, BorderLayout.NORTH);

        // Profile Section
        JPanel profilePanel = new JPanel(new BorderLayout());
        profilePanel.setPreferredSize(new Dimension(900, 200));
        if (selectedUser.getProfilePhotoPath() != null && !selectedUser.getProfilePhotoPath().isEmpty()) {
            profilePictureLabel = new JLabel();
            profilePictureLabel.setIcon(resizeImageIcon(selectedUser.getProfilePhotoPath(), 150, 150));
        } else {
            profilePictureLabel = new JLabel("No Profile Photo", SwingConstants.CENTER);
            profilePictureLabel.setFont(new Font("Arial", Font.BOLD, 16));
            profilePictureLabel.setForeground(Color.GRAY);
        }
        profilePanel.add(profilePictureLabel, BorderLayout.WEST);

        JPanel userDetailsPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        userDetailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        userDetailsPanel.add(new JLabel("Username:"));
        userDetailsPanel.add(new JLabel(selectedUser.getUsername() != null ? selectedUser.getUsername() : "Unknown"));
        userDetailsPanel.add(new JLabel("Email:"));
        userDetailsPanel.add(new JLabel(selectedUser.getEmail() != null ? selectedUser.getEmail() : "Unknown"));
        userDetailsPanel.add(new JLabel("Bio:"));
        userDetailsPanel.add(new JLabel(selectedUser.getBio() != null ? selectedUser.getBio() : "No bio available"));

        profilePanel.add(userDetailsPanel, BorderLayout.CENTER);
        add(profilePanel, BorderLayout.CENTER);

        // Posts Section
        JPanel postsPanel = new JPanel();
        postsPanel.setLayout(new BoxLayout(postsPanel, BoxLayout.Y_AXIS));
        postsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        if (userPosts == null || userPosts.isEmpty()) {
            postsPanel.add(new JLabel("No posts to display."));
        } else {
            for (Post post : userPosts) {
                JPanel postPanel = new JPanel(new BorderLayout());
                postPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

                JLabel contentLabel = new JLabel(post.getContent());
                postPanel.add(contentLabel, BorderLayout.NORTH);

                if (post.getImagePath() != null && !post.getImagePath().isEmpty()) {
                    JLabel imageLabel = new JLabel();
                    imageLabel.setIcon(resizeImageIcon(post.getImagePath(), 300, 200));
                    postPanel.add(imageLabel, BorderLayout.CENTER);
                }

                JLabel timestampLabel = new JLabel(post.getTimestamp().toString());
                timestampLabel.setFont(new Font("Arial", Font.ITALIC, 12));
                postPanel.add(timestampLabel, BorderLayout.SOUTH);

                postsPanel.add(postPanel);
            }
        }

        JScrollPane postsScrollPane = new JScrollPane(postsPanel);
        add(postsScrollPane, BorderLayout.SOUTH);

        setVisible(true);
    }

    private ImageIcon resizeImageIcon(String imagePath, int width, int height) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }
}

