package info.androidhive.project.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by VietAnh on 7/3/2016.
 */
public class Info {
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
/*
    public String toString() {
        return "{\n" +
                "\t\"idtag\": \"" + idTag +"\",\n" +
                "\t\"tag\": \"" +tag + "\",\n" +
                "\t\"srcimg\": \"" + srcImg + "\",\n" +
                "\t\"desc\": \"" + desc + "\",\n" +
                "\t\"uptime\": \"" + uptime + "\",\n" +
                "\t\"place\": \"" + place + "\"\n" +
                "}";
    }*/


}
