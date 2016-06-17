package info.androidhive.project.model;

import java.util.ArrayList;

/**
 * Created by VietAnh on 6/17/2016.
 */
public class ThreeImage {
    private ArrayList<Image> threeImage;

    public ThreeImage() {

    }

    public ThreeImage(ArrayList<Image> threeImage) {
        this.threeImage = threeImage;
    }

    public void setThreeImage(ArrayList<Image> threeImage) {
        this.threeImage = threeImage;
    }

    public ArrayList<Image> getThreeImage() {
        return threeImage;
    }
}
