/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PostInteraction;

import java.time.LocalDateTime;

/**
 *
 * @author mirol
 */
 public class LikeFactory {
        // Static method to create a Like object
        public static Like createLike(String userId) {
            // Use current timestamp when creating a like
            LocalDateTime currentTimestamp = LocalDateTime.now();
            return new Like(userId, currentTimestamp);
        }
    }
