package info.androidhive.project.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by VietAnh on 7/3/2016.
 */
public class InfoTag implements Parcelable {
    private String idTag;
    private String tag;
    private String srcImg;
    private String desc;
    private String uptime;
    private String place;

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setIdTag(String idTag) {
        this.idTag = idTag;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setSrcImg(String srcImg) {
        this.srcImg = srcImg;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setUptime(String uptime) {
        this.uptime = uptime;
    }

    public String getDesc() {
        return desc;
    }

    public String getIdTag() {
        return idTag;
    }

    public String getPlace() {
        return place;
    }

    public String getSrcImg() {
        return srcImg;
    }

    public String getTag() {
        return tag;
    }

    public String getUptime() {
        return uptime;
    }

    public String toString() {
        return "{\n" +
                "\t\"idTag\": \"" + idTag + "\",\n" +
                "\t\"tag\": \"" + tag + "\"\n" +
                "\t\"srcImg\": \"" + srcImg + "\"\n" +
                "\t\"desc\": \"" + desc + "\"\n" +
                "\t\"uptime\": \"" + uptime + "\"\n" +
                "\t\"place\": \"" + place + "\"\n" +
                "}";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.idTag);
        dest.writeString(this.tag);
        dest.writeString(this.srcImg);
        dest.writeString(this.desc);
        dest.writeString(this.uptime);
        dest.writeString(this.place);
    }

    public InfoTag() {
    }

    protected InfoTag(Parcel in) {
        this.idTag = in.readString();
        this.tag = in.readString();
        this.srcImg = in.readString();
        this.desc = in.readString();
        this.uptime = in.readString();
        this.place = in.readString();
    }

    public static final Parcelable.Creator<InfoTag> CREATOR = new Parcelable.Creator<InfoTag>() {
        @Override
        public InfoTag createFromParcel(Parcel source) {
            return new InfoTag(source);
        }

        @Override
        public InfoTag[] newArray(int size) {
            return new InfoTag[size];
        }
    };
}
