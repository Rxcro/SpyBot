package me.recro.spybot.modules.reddit.pojo;

import com.google.gson.annotations.Expose;

/**
 * Created by Admin on 4/16/2017.
 */
public class Post {
    @Expose
    public PostData data;
    @Expose
    public String kind;
}
