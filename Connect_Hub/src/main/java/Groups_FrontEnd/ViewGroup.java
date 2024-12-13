
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Groups_FrontEnd;

/**
 *
 * @author Yara
 */


import Content_Creation.Backend.Post;
import Groups_Backend.Group;
import userdatabasemanagement.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * This class allows a user to view and interact with a group they are part of.
 */
public class ViewGroup {

    private Group group;
    private User selectedUser;

    public ViewGroup(User selectedUser, Group group) {
        this.selectedUser = selectedUser;
        this.group = group;
        initializeGUI();
    }

    /**
     * Initializes the GUI for viewing group details and posts.
     */
    private void initializeGUI() {
        JFrame frame = new JFrame("Group: " + group.getName());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Group Details Panel
        JPanel detailsPanel = new JPanel(new GridLayout(0, 1));
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Group Details"));
        detailsPanel.add(new JLabel("Name: " + group.getName()));
        detailsPanel.add(new JLabel("Description: " + group.getDescription()));
       
        detailsPanel.add(new JLabel("Posts: " + group.getPosts().size()));

        // Group Photo Panel
        JPanel photoPanel = new JPanel();
        photoPanel.setBorder(BorderFactory.createTitledBorder("Group Photo"));
        JLabel photoLabel = new JLabel();
        String photoPath = group.getGroupPhotoPath();
        if (photoPath != null && !photoPath.isEmpty()) {
            ImageIcon photoIcon = new ImageIcon(photoPath);
            Image scaledImage = photoIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            photoLabel.setIcon(new ImageIcon(scaledImage));
        } else {
            photoLabel.setText("No photo available.");
        }
        photoPanel.add(photoLabel);

        // Posts Panel
        JPanel postsPanel = new JPanel();
        postsPanel.setLayout(new BoxLayout(postsPanel, BoxLayout.Y_AXIS));
        postsPanel.setBorder(BorderFactory.createTitledBorder("Posts"));
        JScrollPane scrollPane = new JScrollPane(postsPanel);

        ArrayList<Post> posts = group.getPosts();
        if (posts.isEmpty()) {
            postsPanel.add(new JLabel("No posts available in this group."));
        } else {
            for (Post post : posts) {
                postsPanel.add(new JLabel(post.toString()));
            }
        }

        // Action Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton addPostButton = new JButton("Add Post");
        addPostButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPostAction();
            }
        });

        JButton leaveGroupButton = new JButton("Leave Group");
        leaveGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                leaveGroupAction(frame);
            }
        });

        JButton requestJoinButton = new JButton("Request to Join");
        requestJoinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                requestToJoinAction();
            }
        });

        buttonPanel.add(addPostButton);
        buttonPanel.add(leaveGroupButton);
        if (!isUserMember()) {
            buttonPanel.add(requestJoinButton);
        }

        // Adding panels to the main frame
        mainPanel.add(detailsPanel, BorderLayout.NORTH);
        mainPanel.add(photoPanel, BorderLayout.WEST);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    /**
     * Action for adding a post.
     */
    private void addPostAction() {
    if (isUserMember()) {
        String postContent = JOptionPane.showInputDialog(null, "Enter your post content:", "Add Post", JOptionPane.PLAIN_MESSAGE);
        if (postContent != null && !postContent.trim().isEmpty()) {
            String imagePath = JOptionPane.showInputDialog(null, "Enter the image path (or leave blank for no image):", "Add Post", JOptionPane.PLAIN_MESSAGE);
            if (imagePath != null && imagePath.trim().isEmpty()) {
                imagePath = null;
            }
            LocalDateTime timestamp = LocalDateTime.now(); // Get the current timestamp
            Post newPost = new Post(selectedUser.getId(), postContent, imagePath, timestamp);
            group.addPost(newPost);
            JOptionPane.showMessageDialog(null, "Post added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Post content cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        JOptionPane.showMessageDialog(null, "You must be a member of the group to add posts.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}


    /**
     * Action for leaving the group.
     */
    private void leaveGroupAction(JFrame frame) {
        if (isUserMember()) {
            group.getMembers().remove(selectedUser);
            JOptionPane.showMessageDialog(frame, "You have successfully left the group.", "Success", JOptionPane.INFORMATION_MESSAGE);
            frame.dispose();
        } else {
            JOptionPane.showMessageDialog(frame, "You are not a member of this group.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Action for requesting to join the group.
     */
    private void requestToJoinAction() {
        if (isUserMember()) {
            JOptionPane.showMessageDialog(null, "You are already a member of this group.", "Info", JOptionPane.INFORMATION_MESSAGE);
        } else if (group.getRequestedMembers().contains(selectedUser)) {
            JOptionPane.showMessageDialog(null, "You have already requested to join this group.", "Info", JOptionPane.INFORMATION_MESSAGE);
        } else {
            group.getRequestedMembers().add(selectedUser);
            JOptionPane.showMessageDialog(null, "Request to join the group has been sent.", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Check if the selected user is a member of the group.
     *
     * @return true if the user is a member, false otherwise
     */
    public boolean isUserMember() {
        return group.getMembers().contains(selectedUser);
    }
}