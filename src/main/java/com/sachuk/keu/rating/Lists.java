package com.sachuk.keu.rating;

import java.io.Serializable;
import java.util.ArrayList;

public class Lists implements Serializable {

    private static final long serialVersionUID = 1710069860670231769L;
    private ArrayList<GarrisonGroup> groups = new ArrayList<>();

    public ArrayList<GarrisonGroup> getGroups() {
        return groups;
    }
    public void setGroups(ArrayList<GarrisonGroup> groups) {
        this.groups = groups;
    }

}