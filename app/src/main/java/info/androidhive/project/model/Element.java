package info.androidhive.project.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by VietAnh on 5/16/2016.
 */
public class Element implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.user, flags);
        dest.writeParcelable(this.post, flags);
        dest.writeTypedList(this.tags);
    }

    public Element() {
    }

    protected Element(Parcel in) {
        this.user = in.readParcelable(User.class.getClassLoader());
        this.post = in.readParcelable(Post.class.getClassLoader());
        this.tags = in.createTypedArrayList(Tag.CREATOR);
    }

    public static final Parcelable.Creator<Element> CREATOR = new Parcelable.Creator<Element>() {
        @Override
        public Element createFromParcel(Parcel source) {
            return new Element(source);
        }

        @Override
        public Element[] newArray(int size) {
            return new Element[size];
        }
    };
}
