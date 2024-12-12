/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Content_Creation.Backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

/**
 *
 * @author mirol
 */
public class ContentManagement {

    private String fileName;
    private ArrayList<Post> posts;
    private ArrayList<Story> stories;
    private Post p;
    private Story s;

    private Json j;

    public ContentManagement() {
        j = new Json();
        posts = new ArrayList<>();
        stories = new ArrayList<>();
        s = new Story();
        p = new Post();
        load();
    }

    public void save() {
        try {
            System.out.println("Saving contents: " + posts);
            p.saveToFile(posts);
            System.out.println("Saving contents: " + stories);
            s.saveToFile(stories);
        } catch (Exception e) {
            System.out.println("Error saving contents: " + e.getMessage());
        }
    }

    public void load() {
        try {
            posts = (ArrayList<Post>) p.loadFromFile();
            System.out.println("Loaded posts: " + posts);
            stories = s.loadFromFile();
            System.out.println("Loaded stories: " + stories);
        } catch (Exception e) {
            System.out.println("Error loading contents: " + e.getMessage());
        }
    }

    public void addContent(Content c) {
        if (c != null) {
            if (c instanceof Post) {
                posts.add((Post) c);
                System.out.println("addContent length: " + posts.size());

            } else if (c instanceof Story) {
                stories.add((Story) c);
                System.out.println("addContent length: " + stories.size());
            } else {
                System.out.println("Instance error in add Content");
            }
            save();
        } else {
            System.out.println("Content to add is null!");
        }

    }

    public void deleteContent(Content c) {
        if (c != null) {

            if (c instanceof Post) {
                posts.remove(c);
            } else if (c instanceof Story) {
                stories.remove(c);
            } else {
                System.out.println("Instance error in delete Content");
            }
            save();
        } else {
            System.out.println("Content to delete is null!");
        }
    }

    public Content getContent(String contentId) {
        for (Post p : posts) {
            if (p.contentId.equals(contentId)) {
                return p;
            }
        }
        for (Story s : stories) {
            if (s.contentId.equals(contentId)) {
                return s;
            }
        }
        System.out.println("Content with this id doesn't exist!");
        return null;
    }

    public ArrayList<Content> getcontentByAuthorId(String authorId) {
        ArrayList<Content> allContent = new ArrayList<>();
        for (Post c : posts) {
            if (c.getAuthorId().equals(authorId)) {
                allContent.add(c);
            }
        }
        for (Story c : stories) {
            if (c.getAuthorId().equals(authorId)) {
                allContent.add(c);
            }
        }
        System.out.println("Fetched content for author " + authorId + ": " + allContent);
        return allContent;
    }

    public void deleteExpiredStories() {
        for (Story c : stories) {
            if (c.isExpired()) {
                deleteContent(c);
            }
        }
        save();
    }

    public ArrayList<Post> getposts() {
        return posts;
    }

    public ArrayList<Story> getstories() {
        return stories;
    }

}
