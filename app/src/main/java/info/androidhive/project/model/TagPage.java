package info.androidhive.project.model;

/**
 * Created by VietAnh on 7/3/2016.
 */
public class TagPage {
    private Elements elements;
    private InfoTag infoTag;

    public void setElements(Elements elements) {
        this.elements = elements;
    }

    public void setInfoTag(InfoTag infoTag) {
        this.infoTag = infoTag;
    }

    public Elements getElements() {
        return elements;
    }

    public InfoTag getInfoTag() {
        return infoTag;
    }

    public String toString() {
        return "{\n" +
                "\t\"elements\":" + elements.toString() + ",\n" +
                "\t\"infoTag\":" + infoTag.toString() + ",\n" +
                "}";
    }


}
