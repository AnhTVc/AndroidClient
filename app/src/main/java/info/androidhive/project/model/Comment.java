package info.androidhive.project.model;


/**
 * Created by VietAnh on 7/7/2016.
 */
public class Comment {
    private User user;
    private String content;
    private String timeCreate;

    public void setContent(String content) {
        this.content = content;
    }

    public void setTimeCreate(String timeCreate) {
        this.timeCreate = timeCreate;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public String getTimeCreate() {
        return timeCreate;
    }

    public User getUser() {
        return user;
    }

    public String toString() {
        return "{\n" +
                "\t\"user\": " + user.toString() + ",\n" +
                "\t\"content\": \"" + content + "\",\n" +
                "\t\"time_create\": \"" + timeCreate + "\"\n" +
                "}";
    }

    public static void main(String[] main) {
        Comment comment = new Comment();
        User user = new User();
        user.setNameUser("Nai Nguyen");
        Image image = new Image();
        user.setImage(image);

        comment.setUser(user);
        comment.setContent("Content");
        comment.setTimeCreate("12s");

        System.out.print(comment.toString());
    }
}
