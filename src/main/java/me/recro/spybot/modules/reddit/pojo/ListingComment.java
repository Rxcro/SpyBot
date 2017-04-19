package me.recro.spybot.modules.reddit.pojo;

/**
 * Created by Admin on 4/16/2017.
 */
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Siddharth Verma on 24/4/16.
 */
public class ListingComment {

    @Expose
    public List<Comment> children;
    @Expose
    public String modhash;
    @Expose
    public String before;
    @Expose
    public String after;

    public ListingComment() {
        this.children = new ArrayList<>();
    }
}
