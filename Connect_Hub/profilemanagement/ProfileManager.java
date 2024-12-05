/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package profilemanagement;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HP
 */
public class ProfileManager {
    private final UserRepository userRepository;
   private final PostRepository postRepository;

    public ProfileManager(UserRepository userRepository , PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository=postRepository;
    }

    public User getUserProfile(String userId) throws IllegalArgumentException {
        return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

   public List<Post> getUserPosts(String userId){
        return postRepository.findPostsByUserId(userId);
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
    String hashedPassword = PasswordUtil.hashPassword(newPassword); // Hash the password
    userRepository.updatePassword(userId, hashedPassword);
}
   
}