package info.androidhive.project.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by VietAnh on 5/16/2016.
 */
public class Image implements Parcelable {
    private String nameImg;
    private int height;
    private int weight;
    private String src;

    public int getHeight() {
        return height;
    }

    public String getNameImg() {
        return nameImg;
    }

    public String getSrc() {
        return src;
    }

    public int getWeight() {
        return weight;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setNameImg(String nameImg) {
        this.nameImg = nameImg;
    }

    public void setSrc(String urlImg) {
        this.src = urlImg;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String toString(){
        return "{\n" +
                "\t\"nameImg\": \"" + nameImg + "\",\n" +
                "\t\"height\": \"" + height + "\",\n" +
                "\t\"weight\": \"" + weight + "\",\n" +
                "\t\"src\": \"" + src + "\"\n" +
                "}";
    }


    public String getNameObject(){
        return "com.Project.POJO.Image";
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nameImg);
        dest.writeInt(this.height);
        dest.writeInt(this.weight);
        dest.writeString(this.src);
    }

    public Image() {
    }

    protected Image(Parcel in) {
        this.nameImg = in.readString();
        this.height = in.readInt();
        this.weight = in.readInt();
        this.src = in.readString();
    }

    public static final Parcelable.Creator<Image> CREATOR = new Parcelable.Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel source) {
            return new Image(source);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };
}
