package info.androidhive.project.model;

/**
 * Created by VietAnh on 5/16/2016.
 */
public class Image {
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
}
