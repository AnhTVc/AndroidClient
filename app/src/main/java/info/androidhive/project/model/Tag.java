package info.androidhive.project.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by VietAnh on 5/16/2016.
 */
public class Tag implements Parcelable {
    private String idTag;

    private String nameTag;

    public void setNameTag(String nameTag) {
        this.nameTag = nameTag;
    }

    public String getNameTag() {

        return nameTag;
    }

    public String getIdTag() {
        return idTag;
    }

    public void setIdTag(String idTag) {
        this.idTag = idTag;
    }

    public String toString(){
        return "{\n" +
                "\t\"idTag\": \"" + idTag + "\",\n" +
                "\t\"nameTag\": \"" + nameTag + "\"\n" +
                "}";
    }

    public String getNameObject(){
        return "com.Project.POJO.Tag";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.idTag);
        dest.writeString(this.nameTag);
    }

    public Tag() {
    }

    protected Tag(Parcel in) {
        this.idTag = in.readString();
        this.nameTag = in.readString();
    }

    public static final Parcelable.Creator<Tag> CREATOR = new Parcelable.Creator<Tag>() {
        @Override
        public Tag createFromParcel(Parcel source) {
            return new Tag(source);
        }

        @Override
        public Tag[] newArray(int size) {
            return new Tag[size];
        }
    };
}
