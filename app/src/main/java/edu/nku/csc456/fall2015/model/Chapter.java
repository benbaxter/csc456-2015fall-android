package edu.nku.csc456.fall2015.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Benjamin on 8/22/2015.
 */
public class Chapter {

    public String date;
    public List<String> readings;
    public List<String> topics;
    public List<String> slides;

    @SerializedName("boss-fight")
    public boolean isBossFight;

    @SerializedName("no-class")
    public boolean noClass;

    @SerializedName("project-demo")
    public boolean projectDemo;
}
