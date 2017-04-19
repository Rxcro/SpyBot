package me.recro.spybot.modules.reddit.pojo;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 4/16/2017.
 */
public class Listing {

    @Expose
    public List<Post> children;
    @Expose
    public String modhash;
    @Expose
    public String before;
    @Expose
    public String after;

    public Listing() {
        this.children = new ArrayList<>();
    }
}