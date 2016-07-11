package info.androidhive.project.model;

import java.util.ArrayList;

/**
 * Created by VietAnh on 7/11/2016.
 */
public class Comments {
    public ArrayList<Comment> getComments() {
        return comments;
    }

    ArrayList<Comment> comments;

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }
}
