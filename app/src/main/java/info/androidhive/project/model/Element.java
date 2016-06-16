package info.androidhive.project.model;

import java.util.ArrayList;

/**
 * Created by VietAnh on 5/16/2016.
 */
public class Element {
    private User user;
    private Post post;
    private ArrayList<Tag> tags;

    public Post getPost() {
        return post;
    }


    public User getUser() {
        return user;
    }

    public ArrayList<Tag> getTag() {
        return tags;
    }

    public void setTag(ArrayList<Tag> tag) {
        this.tags = tag;
    }

    public void setPost(Post post) {
        this.post = post;
    }


    public void setUser(User user) {
        this.user = user;
    }

    public String getNameObject(){
        return "com.Project.POJO.Element";
    }

    public String toString(){
        String temp = "";

        for (int i = 0; i < tags.size(); i++) {
            temp += tags.get(i).toString();
            if (i + 1 < tags.size()) {
                temp += ",";
            }
        }

        return "{\n" +
                "\t\"user\":" + user.toString() + ",\n" +
                "\t\"post\":" + post.toString() + ",\n" +

                "\t\"tags\":[" + temp + "]\n" +
                "}";
    }
}
