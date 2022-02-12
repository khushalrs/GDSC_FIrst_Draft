package com.android.gdsc;

public class FAQList {
    private String name;
    private String answer;
    private Boolean expanded;

    public FAQList(String name, String answer) {
        this.name = name;
        this.answer = answer;
        this.expanded = false;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAnswer() {
        return answer;
    }

    public void setimage(String image) {
        this.answer = image;
    }
    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}
