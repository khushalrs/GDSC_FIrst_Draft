package com.android.gdsc;

public class HomeList {
    private String name;
    private String image;

    public HomeList(){}

    public HomeList(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setimage(String image) {
        this.image = image;
    }
}
