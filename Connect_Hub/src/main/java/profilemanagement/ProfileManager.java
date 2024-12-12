/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package profilemanagement;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import userdatabasemanagement.Encryptor;
import userdatabasemanagement.User;
import Content_Creation.Backend.Post;
/**
 *
 * @author HP
 */
public class ProfileManager {
   public  final UserRepository userRepository;
   private final PostRepository postRepository;

    public ProfileManager(UserRepository userRepository , PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository=postRepository;
    }

    public Object getUserProfile(String userId) throws IllegalArgumentException {
        return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

   // Fetch user posts by ID
    public List<Post> getUserPosts(String userId) {
        // Make sure postRepository is properly fetching posts
        List<Post> posts = postRepository.findPostsByUserId(userId);
        if (posts == null) {
            System.out.println("No posts found for user: " + userId);
        }
        return posts;
    }

    public List <User> getFriends(String UserId){
        return userRepository.getFriends(UserId);
    }
    public void updateBio(String userId, String newBio) throws IOException {
        try {
            userRepository.updateField(userId, "bio", newBio);
          
        } catch (Exception ex) {
            Logger.getLogger(ProfileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   public void updateProfilePhoto(String userId, String profilePhotoPath) throws IOException {
    userRepository.updateProfilePhoto(userId, profilePhotoPath);
}

public void updateCoverPhoto(String userId, String coverPhotoPath) throws IOException {
    userRepository.updateCoverPhoto(userId, coverPhotoPath);
}

    public void updatePassword(String userId, String newPassword) throws IOException, Exception {
    String hashedPassword; // Hash the password
     Encryptor encryptedPassword= new Encryptor();
     hashedPassword= encryptedPassword.encryptPassword(newPassword);

    userRepository.updatePassword(userId, hashedPassword);
}
   
}