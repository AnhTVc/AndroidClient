package info.androidhive.project.model;

import java.util.ArrayList;

/**
 * Created by VietAnh on 5/17/2016.
 */
public class Elements {
    private ArrayList<Element> elements;

    public void setElements(ArrayList<Element> elements) {
        this.elements = elements;
    }

    public ArrayList<Element> getElements() {

        return elements;
    }

    public String toString(){
        String temp = "";

        for(int i = 0; i< elements.size(); i++){
            temp += elements.get(i).toString();
            if(i + 1 < elements.size()){
                temp += ",";
            }
        }
        return "[" + temp + "]";
    }
}
