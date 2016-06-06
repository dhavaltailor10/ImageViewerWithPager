package com.maison.test.image_viewer.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Christophe on 26/05/2016.
 */
public class Hero implements Serializable {

    private String titre;
    private String intro;
    private String year;
    private String text;
    private String color;
    private ArrayList<String> imageList;

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public ArrayList<String> getImageList() {
        return imageList;
    }

    public void setImageList(ArrayList<String> imageList) {
        this.imageList = imageList;
    }
}
