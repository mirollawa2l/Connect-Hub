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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import java.util.List;
import java.util.stream.Collectors;
import Content_Creation.Backend.Post;
import Content_Creation.Backend.*;
public class PostRepository { 
    private static final String POST_FILE = "contentDatabase.json"; // Merge content file name
    private final ObjectMapper objectMapper = new ObjectMapper(); 
    private List<Content> contents;  // Store both posts and stories
 
    public PostRepository() throws IOException { 
        loadPosts(); 
    } 
 
    private void loadPosts() throws IOException { 
        File file = new File(POST_FILE); 
        if (file.exists()) { 
            contents = objectMapper.readValue(file, new TypeReference<List<Content>>() {}); 
        } else { 
            contents = new ArrayList<>(); 
        } 
    }

    // Fetch posts by user ID
    public List<Post> findPostsByUserId(String userId) {
        // Filter out only posts (isStory == false) and by userId
        return contents.stream()
                .filter(content -> !content.isStory() && content.getAuthorId().equals(userId))  // Filtering only posts
                .map(content -> (Post) content)  // Cast content to Post
                .collect(Collectors.toList());
    }

    // Additional methods to add posts, save, etc...


    // Add a new post
    /*public void addPost(Post post) {
        if (post != null) {
            posts.add(post);
            savePosts();  // Save the updated posts list to the file
        }
    }*/
}