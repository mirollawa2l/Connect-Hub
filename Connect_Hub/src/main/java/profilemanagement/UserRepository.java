/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package profilemanagement;

/**
 *
 * @author HP
 */
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import userdatabasemanagement.User;

public class UserRepository {
    private static final String USER_FILE = "users.json";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private List<User> users;

    public UserRepository() throws IOException {
        loadUsers();
    }

    // Load users from JSON file
    private void loadUsers() throws IOException {
        File file = new File(USER_FILE);
        if (file.exists()) {
            users = objectMapper.readValue(file, new TypeReference<List<User>>() {}); // read json to java object // TypeReference<List<User>>() {} -> Specifies the type of data being read
        } else {
            users = new ArrayList<>(); // empty arraylist created 
            saveUsers(); // Create an empty file if it doesn't exist
        }
    }

    // Save users to JSON file
    private void saveUsers() throws IOException {
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(USER_FILE), users);// writing java object into json
    }

    // Find user by ID
    public Optional<User> findById(String userId) {
        return users.stream()
                .filter(user -> user.getId().equals(userId)) // filter users by user id
                .findFirst(); // return first user that matches with the user id
    }
    public void updateProfilePhoto(String userId, String profilePhotoPath) throws IOException {
    User user = findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
    user.setProfilePhotoPath(profilePhotoPath);
    saveUsers();
}

public void updateCoverPhoto(String userId, String coverPhotoPath) throws IOException {
    User user = findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
    user.setCoverPhotoPath(coverPhotoPath);
    saveUsers();
}

    // Save or update user
    public void saveUser(User updatedUser) throws IOException {
        users.removeIf(user -> user.getId().equals(updatedUser.getId()));
        users.add(updatedUser);
        saveUsers();
    }

    // Update a specific field in a user record
    public void updateField(String userId, String field, String value) throws IOException {
        User user = findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
        switch (field.toLowerCase()) {
            case "bio" -> user.setBio(value);
            case "profilephoto" -> user.setProfilePhotoPath(value);
            case "coverphoto" -> user.setCoverPhotoPath(value);
            case "status" -> user.setStatus(value);
            default -> throw new IllegalArgumentException("Invalid field: " + field);
        }
        saveUser(user);
    }

    // Update user password
    public void updatePassword(String userId, String hashedPassword) throws IOException {
        User user = findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
        user.setPassword(hashedPassword);
        saveUser(user);
    }

    // Check if a user exists
    public boolean userExists(String userId) {
        return findById(userId).isPresent();
    }

    // Retrieve all users
    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    // Add a friend to a user
    public void addFriend(String userId, String friendId) throws IOException {
        if (!userExists(friendId)) {
            throw new IllegalArgumentException("Friend ID not found: " + friendId);
        }
        User user = findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
        if (!user.getFriends().contains(friendId)) {
            user.getFriends().add(friendId);
            saveUser(user);
        }
    }

    // Remove a friend from a user
    public void removeFriend(String userId, String friendId) throws IOException {
        User user = findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
        if (user.getFriends().remove(friendId)) {
            saveUser(user);
        } else {
            throw new IllegalArgumentException("Friend ID not found in user's friend list: " + friendId);
        }
    }
    public List<User> getFriends(String userId) {
        Optional<User> userOptional = findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get(); // unwrap user object
            // Assuming a friends field exists in User containing the list of friend IDs
            return user.getFriends().stream()
                    .map(friendId -> findById(friendId).orElse(null))
                    .filter(friend -> friend != null)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>(); // empty arraylist not null to avoid null pointer exception
    }
}