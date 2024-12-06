/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package profilemanagement;

import javax.swing.JFrame;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import userdatabasemanagement.User;
/**
 *
 * @author HP
 */

public class ProfileGUI extends JFrame {
    private final ProfileManager profileManager;
    private User currentUser;
    private JLabel profilePictureLabel;
    private JLabel coverPictureLabel;

    public ProfileGUI(ProfileManager profileManager, User user) {
        this.profileManager = profileManager;

        try {
            // Load the current user from ProfileManager
            currentUser = user;
            initComponents();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private void initComponents() {
    // Frame settings
    setTitle("Profile Management");
    setSize(900, 700);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setLayout(new BorderLayout());

    
    

    // Cover Photo Section
    JPanel coverPanel = new JPanel(new BorderLayout());
    coverPanel.setPreferredSize(new Dimension(900, 250));
     if (currentUser.getCoverPhotoPath() != null && !currentUser.getCoverPhotoPath().isEmpty()) {
        coverPictureLabel = new JLabel();
        coverPictureLabel.setIcon(resizeImageIcon(currentUser.getCoverPhotoPath(), 900, 250));
    } else {
        coverPictureLabel = new JLabel("No Cover Photo", SwingConstants.CENTER);
        coverPictureLabel.setFont(new Font("Arial", Font.BOLD, 18));
        coverPictureLabel.setForeground(Color.GRAY);
    }

    coverPanel.add(coverPictureLabel, BorderLayout.CENTER);
    JButton uploadCoverButton = new JButton("Upload Cover Photo");
    uploadCoverButton.addActionListener(e -> uploadCoverPhoto());
    coverPanel.add(uploadCoverButton, BorderLayout.SOUTH);
    add(coverPanel, BorderLayout.NORTH);

    // Profile Details Section
    JPanel mainPanel = new JPanel(new BorderLayout());
    JPanel profilePanel = new JPanel(new BorderLayout());
     if (currentUser.getProfilePhotoPath() != null && !currentUser.getProfilePhotoPath().isEmpty()) {
        profilePictureLabel = new JLabel();
        profilePictureLabel.setIcon(resizeImageIcon(currentUser.getProfilePhotoPath(), 150, 150));
    } else {
        profilePictureLabel = new JLabel("No Profile Photo", SwingConstants.CENTER);
        profilePictureLabel.setFont(new Font("Arial", Font.BOLD, 16));
        profilePictureLabel.setForeground(Color.GRAY);
    }

    profilePanel.add(profilePictureLabel, BorderLayout.CENTER);
    JButton uploadProfileButton = new JButton("Upload Profile Picture");
    uploadProfileButton.addActionListener(e -> uploadProfilePhoto());
    profilePanel.add(uploadProfileButton, BorderLayout.SOUTH);
    mainPanel.add(profilePanel, BorderLayout.WEST);

    JPanel detailsPanel = new JPanel(new GridLayout(6, 2, 10, 10));
    detailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    detailsPanel.add(new JLabel("Username:"));
    detailsPanel.add(new JLabel(currentUser.getUsername()));
        System.out.println(currentUser.toString());
        System.out.println(currentUser.getUsername());
    detailsPanel.add(new JLabel("Email:"));
    detailsPanel.add(new JLabel(currentUser.getEmail()));
          System.out.println(currentUser.getEmail());
    detailsPanel.add(new JLabel("Bio:"));
    JTextField bioField = new JTextField(currentUser.getBio());
    detailsPanel.add(bioField);
    detailsPanel.add(new JLabel("New Password:"));
        JPasswordField passwordField = new JPasswordField();
        detailsPanel.add(passwordField);
        detailsPanel.add(new JLabel("Confirm Password:"));
        JPasswordField confirmPasswordField = new JPasswordField();
        detailsPanel.add(confirmPasswordField);
    JButton saveButton = new JButton("Save Changes");
    saveButton.setBackground(new Color(46, 139, 87));
    saveButton.setForeground(Color.WHITE);
    saveButton.addActionListener(e -> {
            try {
                String password = new String(passwordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());

                if (!password.isEmpty()) {
                    if (!password.equals(confirmPassword)) {
                        JOptionPane.showMessageDialog(this, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    try {
                        profileManager.updatePassword(currentUser.getId(), password);
                    } catch (Exception ex) {
                        Logger.getLogger(ProfileGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                profileManager.updateBio(currentUser.getId(), bioField.getText());
                JOptionPane.showMessageDialog(this, "Profile updated successfully!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving changes: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    detailsPanel.add(new JLabel());
    detailsPanel.add(saveButton);
    mainPanel.add(detailsPanel, BorderLayout.CENTER);
    add(mainPanel, BorderLayout.CENTER);

    // Tabbed Pane with Listeners for New Frames
    JTabbedPane tabbedPane = new JTabbedPane();
    tabbedPane.addTab("Posts", null); // Placeholder
    tabbedPane.addTab("Friends", null); // Placeholder

    tabbedPane.addChangeListener(e -> {
        int selectedTab = tabbedPane.getSelectedIndex();
        if (selectedTab == 0) {
            openPostsFrame();
        } else if (selectedTab == 1) {
            openFriendsFrame();
        }
    });
    JPanel tab = new JPanel();
    tab.add(tabbedPane);
    tab.setPreferredSize(new Dimension(70 , 70));

    add(tab, BorderLayout.AFTER_LINE_ENDS);
    // Add footer panel
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(70, 130, 180));
        JLabel footerLabel = new JLabel("© 2024 Connect Hub. All rights reserved.");
        footerLabel.setForeground(Color.WHITE);
        footerPanel.add(footerLabel);
        add(footerPanel, BorderLayout.PAGE_END);

    setVisible(true);
} 

private void openPostsFrame() {
    JFrame postsFrame = new JFrame("User Posts");
    postsFrame.setSize(600, 400);
    postsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    postsFrame.setLocationRelativeTo(this);

    JPanel postsPanel = new JPanel();
    postsPanel.setLayout(new BoxLayout(postsPanel, BoxLayout.Y_AXIS));
    List<Post> posts = profileManager.getUserPosts(currentUser.getId());

    if (posts.isEmpty()) {
        postsPanel.add(new JLabel("No posts to display."));
    } else {
        for (Post post : posts) {
            JPanel postPanel = new JPanel(new BorderLayout());
            JLabel contentLabel = new JLabel(post.getContent());
            JLabel timestampLabel = new JLabel(post.getTimestamp());
            timestampLabel.setFont(new Font("Arial", Font.ITALIC, 12));

            if (post.getImagePath() != null) {
                JLabel imageLabel = new JLabel(new ImageIcon(post.getImagePath()));
                imageLabel.setPreferredSize(new Dimension(300, 200));
                postPanel.add(imageLabel, BorderLayout.CENTER);
            }

            postPanel.add(contentLabel, BorderLayout.NORTH);
            postPanel.add(timestampLabel, BorderLayout.SOUTH);
            postPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            postsPanel.add(postPanel);
        }
    }

    postsFrame.add(new JScrollPane(postsPanel));
    postsFrame.setVisible(true);
}


private void openFriendsFrame() {
    
    JFrame friendsFrame = new JFrame("Friends List");
    friendsFrame.setSize(600, 400);
    friendsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    friendsFrame.setLocationRelativeTo(this);

    JPanel friendsPanel = new JPanel();
    friendsPanel.setLayout(new BoxLayout(friendsPanel, BoxLayout.Y_AXIS)); // Vertical layout

    List<User> friends = profileManager.getFriends(currentUser.getId());
    if (friends.isEmpty()) {
        friendsPanel.add(new JLabel("You have no friends."));
    } else {
        for (User friend : friends) {
            // Create panel for each friend
            JPanel friendPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            friendPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            // Profile Picture
            JLabel profilePicLabel = new JLabel();
            if (friend.getProfilePhotoPath() != null && !friend.getProfilePhotoPath().isEmpty()) {
                profilePicLabel.setIcon(resizeImageIcon(friend.getProfilePhotoPath(), 30, 30)); // Resize the profile photo
            } else {
                // If no photo, use a default placeholder
                profilePicLabel.setIcon(new ImageIcon(getClass().getResource("/default_profile_picture.png")));
            }

            // Name and Status
            JLabel nameLabel = new JLabel(friend.getUsername());
            JLabel statusLabel = new JLabel(friend.getStatus().equalsIgnoreCase("online") ? "(Online)" : "(Offline)");

            // Style for online friends
            if ("online".equalsIgnoreCase(friend.getStatus())) {
                nameLabel.setForeground(Color.GREEN);  // Green for online friends
                statusLabel.setForeground(Color.GREEN);
            } else {
                nameLabel.setForeground(Color.GRAY);  // Gray for offline friends
                statusLabel.setForeground(Color.GRAY);
            }

            // Add profile picture, name, and status to the panel
            friendPanel.add(profilePicLabel);
            friendPanel.add(nameLabel);
            friendPanel.add(statusLabel);

            // Add the friend panel to the friends list
            friendsPanel.add(friendPanel);
        }
    }

    friendsFrame.add(new JScrollPane(friendsPanel));  // Scroll if too many friends
    friendsFrame.setVisible(true);
}
private void uploadProfilePhoto() {
    String filePath = uploadFile();
    if (filePath != null) {
        try {
            profileManager.updateProfilePhoto(currentUser.getId(), filePath);
            currentUser = (User) profileManager.getUserProfile(currentUser.getId());

            // Set the image and remove placeholder text
            profilePictureLabel.setIcon(resizeImageIcon(filePath, 150, 150));
            profilePictureLabel.setText(""); // Remove "No Profile Photo"
            JOptionPane.showMessageDialog(this, "Profile photo updated successfully!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error updating profile photo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
    private void uploadCoverPhoto() {
    String filePath = uploadFile();
    if (filePath != null) {
        try {
            profileManager.updateCoverPhoto(currentUser.getId(), filePath);
            currentUser = (User) profileManager.getUserProfile(currentUser.getId());

            // Set the image and remove placeholder text
            coverPictureLabel.setIcon(resizeImageIcon(filePath, 900, 250));
            coverPictureLabel.setText(""); // Remove "No Cover Photo"
            JOptionPane.showMessageDialog(this, "Cover photo updated successfully!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error updating cover photo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

    private String uploadFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int returnValue = fileChooser.showOpenDialog(this);


if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();

            if (isImageFile(filePath)) {
                return filePath;
            } else {
                JOptionPane.showMessageDialog(this, "Invalid file type. Please select an image file (e.g., .jpg, .png).", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return null;
    }

    private boolean isImageFile(String filePath) {
        String lowerCasePath = filePath.toLowerCase();
        return lowerCasePath.endsWith(".jpg") || lowerCasePath.endsWith(".jpeg") || lowerCasePath.endsWith(".png")
                 ||lowerCasePath.endsWith(".bmp") || lowerCasePath.endsWith(".gif");
    }

    private ImageIcon resizeImageIcon(String imagePath, int width, int height) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    public static void main(String[] args) throws Exception {
        UserRepository userRepository = new UserRepository();
       PostRepository postRepository = new PostRepository();
        ProfileManager profileManager = new ProfileManager(userRepository , postRepository );

    }
}