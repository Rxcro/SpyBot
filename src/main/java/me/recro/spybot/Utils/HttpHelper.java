package me.recro.spybot.Utils;

/**
 * Created by Admin on 4/16/2017.
 */
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import me.recro.spybot.Config;

/**
 * Created on 8-9-2016
 */
public class HttpHelper {

    /**
     * @param url the url to request to
     * @return a string containing the response
     */
    public static String doRequest(String url) {
        try {
            return Unirest.get(url).header("User-Agent", Config.USER_AGENT).asString().getBody();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return "";
    }
}
