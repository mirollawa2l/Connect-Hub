package PostInteraction;

public class TimestampedCommentDecorator extends CommentDecorator {

    public TimestampedCommentDecorator(Comment comment) {
        super(comment);
    }

    @Override
    public String decorate() {
        return "[" + getTimestamp() + "] " + getText();
    }
}
