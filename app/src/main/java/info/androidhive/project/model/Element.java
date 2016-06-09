package info.androidhive.project.model;

/**
 * Created by VietAnh on 5/16/2016.
 */
public class Element {
    private User user;
    private Post post;
    private Tag tag;

    public Post getPost() {
        return post;
    }

    public Tag getTag() {
        return tag;
    }

    public User getUser() {
        return user;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getNameObject(){
        return "com.Project.POJO.Element";
    }

    public String toString(){
        return "{\n" +
                "\t\"user\":" + user.toString() + ",\n" +
                "\t\"post\":" + post.toString() + ",\n" +
                "\t\"tag\":" + tag.toString() + "\n" +
                "}";
    }
}
