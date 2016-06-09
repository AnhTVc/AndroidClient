package info.androidhive.project.model;

/**
 * Created by VietAnh on 5/16/2016.
 */
public class Tag {
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
}
