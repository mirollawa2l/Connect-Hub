package PostInteraction;

import Content_Creation.Backend.Content;
import java.util.List;

public class ContentAdapter implements CommentProvider {
    private final Content content;

    public ContentAdapter(Content content) {
        this.content = content;
    }

    @Override
    public List<Comment> getComments() {
        return content.getComments();
    }
}
