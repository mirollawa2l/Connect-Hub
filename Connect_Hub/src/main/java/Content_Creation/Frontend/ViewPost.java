/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Content_Creation.Frontend;

import Content_Creation.Backend.Content;
import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author sherrygirguis
 */
public class ViewPost {

    
    public void showPost(Content content){
     // Create a new JFrame for the detailed view
    JFrame viewPostFrame = new JFrame("Post Details");
    viewPostFrame.setSize(600, 400);  // Set size of the new window
    viewPostFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    
    // Create a panel to display the post details
    JPanel viewPostPanel = new JPanel();
    viewPostPanel.setLayout(new BorderLayout());

    
     // Content: Text area for post content
        if (content.getContent() != null && !content.getContent().isEmpty()) {
            JTextArea contentArea = new JTextArea(content.getContent());
            contentArea.setLineWrap(true);
            contentArea.setWrapStyleWord(true);
            contentArea.setEditable(false); // Make the content non-editable
            viewPostPanel.add(new JScrollPane(contentArea), BorderLayout.CENTER);
        }

        // Image: Display if available
        if (content.getImagePath() != null && !content.getImagePath().isEmpty()) {
            try {
                ImageIcon imageIcon = new ImageIcon(content.getImagePath()); // Load image from path
                JLabel imageLabel = new JLabel();
                imageLabel.setIcon(imageIcon);
               viewPostPanel.add(imageLabel, BorderLayout.CENTER);
            } catch (Exception e) {
                System.err.println("Error loading image for content: " + content.getContentId());
                e.printStackTrace();
            }
        }
    }}

