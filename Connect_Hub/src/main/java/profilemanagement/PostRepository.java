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
import Content_Creation.Backend.Post;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
public class PostRepository { 
    private static final String POST_FILE = "postsDatabase.json"; // Merge content file name
    private final ObjectMapper objectMapper = new ObjectMapper(); 
    private List<Post> posts;  
 
    public PostRepository() throws IOException { 
        loadFromFile(); 
    } 
 
   
    public void loadFromFile() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());


        File file = new File(POST_FILE);
        List<Post> posts = new ArrayList<>();

        try {
            if (file.exists() && file.length() > 0) {
                posts = objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, Post.class));
                System.out.println("Posts loaded successfully from: " +POST_FILE );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.posts=posts;
    }

    // Fetch posts by user ID
 public List<Post> findPostsByUserId(String userId) {
        return posts.stream().filter(post -> post.getAuthorId().equals(userId)).collect(Collectors.toList());
 }

}