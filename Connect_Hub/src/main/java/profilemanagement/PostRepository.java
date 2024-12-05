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

public class PostRepository {
    private static final String POST_FILE = "posts.json";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private List<Post> posts;

    public PostRepository() throws IOException {
        loadPosts();
    }

    private void loadPosts() throws IOException {
        File file = new File(POST_FILE);
        if (file.exists()) {
            posts = objectMapper.readValue(file, new TypeReference<List<Post>>() {});
        } else {
            posts = new ArrayList<>();
        }
    }

    public List<Post> findPostsByUserId(String userId) {
        return posts.stream().filter(post -> post.getAuthorId().equals(userId)).collect(Collectors.toList());
    }
}