package PostInteraction;

import java.time.LocalDateTime;

public abstract class CommentDecorator extends Comment { //Decoder
    protected Comment comment;

    public CommentDecorator(Comment comment) {
        this.comment = comment;
    }

    @Override
    public String getAuthorId() {
        return comment.getAuthorId();
    }

    @Override
    public String getText() {
        return comment.getText();
    }

    @Override
    public LocalDateTime getTimestamp() {
        return comment.getTimestamp();
    }

    public abstract String decorate();
}
