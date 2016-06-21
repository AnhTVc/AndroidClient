package info.androidhive.project.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by VietAnh on 5/16/2016.
 */
public class Post implements Parcelable {
    private String idPost;
    private String createPost;
    private String countTruePost;
    private String countFalsePost;
    ArrayList<Image> images;

    private String contentPost;

    private String idUser;
    private String idTag;

    public String getContentPost() {
        return contentPost;
    }

    public String getCountFalsePost() {
        return countFalsePost;
    }

    public String getCountTruePost() {
        return countTruePost;
    }

    public String getCreatePost() {
        return createPost;
    }

    public String getIdPost() {
        return idPost;
    }

    public String getIdTag() {
        return idTag;
    }

    public String getIdUser() {
        return idUser;
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    public void setContentPost(String contentPost) {
        this.contentPost = contentPost;
    }

    public void setCountFalsePost(String countFalsePost) {
        this.countFalsePost = countFalsePost;
    }

    public void setCountTruePost(String countTruePost) {
        this.countTruePost = countTruePost;
    }

    public void setCreatePost(String createPost) {
        this.createPost = createPost;
    }

    public void setIdPost(String idPost) {
        this.idPost = idPost;
    }

    public void setIdTag(String idTag) {
        this.idTag = idTag;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public void setImages(ArrayList<Image> images) {
        this.images = images;
    }

    public String getNameObject(){
        return "com.Project.POJO.Post";
    }

    public String toString(){
        String temp = "";
        for(int i = 0; i < images.size(); i++){
            temp += images.get(i).toString();
            if(i + 1 < images.size()){
                temp += ",";
            }
        }

        return "{\n" +
                "\t\"idPost\":\"" + idPost + "\",\n" +
                "\t\"contentPost\":\"" + contentPost + "\",\n" +
                "\t\"timeCreatePost\":\"" + createPost + "\",\n" +
                "\t\"countTruePost\":\"" + countTruePost + "\",\n" +
                "\t\"countFalsePost\":\"" + countFalsePost + "\",\n" +
                "\t\"images\":[" + temp + "]\n" +
                "}";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.idPost);
        dest.writeString(this.createPost);
        dest.writeString(this.countTruePost);
        dest.writeString(this.countFalsePost);
        dest.writeTypedList(this.images);
        dest.writeString(this.contentPost);
        dest.writeString(this.idUser);
        dest.writeString(this.idTag);
    }

    public Post() {
    }

    protected Post(Parcel in) {
        this.idPost = in.readString();
        this.createPost = in.readString();
        this.countTruePost = in.readString();
        this.countFalsePost = in.readString();
        this.images = in.createTypedArrayList(Image.CREATOR);
        this.contentPost = in.readString();
        this.idUser = in.readString();
        this.idTag = in.readString();
    }

    public static final Parcelable.Creator<Post> CREATOR = new Parcelable.Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel source) {
            return new Post(source);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };
}
