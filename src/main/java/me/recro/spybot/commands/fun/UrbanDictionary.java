package me.recro.spybot.commands.fun;

import com.google.api.client.repackaged.com.google.common.base.Joiner;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import me.recro.spybot.Config;
import me.recro.spybot.Utils.Emojibet;
import me.recro.spybot.commands.Command;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by Admin on 4/16/2017.
 */
public class UrbanDictionary extends Command {

    @Override
    public void executeCommand(String[] args, MessageReceivedEvent e, MessageSender chat) {
        if(args.length==0) {
            chat.sendMessage("Invalid usage");
            return;
        }
        String search = Joiner.on(" ").join(args);
        try {
            Future<HttpResponse<JsonNode>> future = Unirest.get("http://api.urbandictionary.com/v0/define?term=" + URLEncoder.encode(search, "UTF-8")).asJsonAsync();
            HttpResponse<JsonNode> json = future.get(30, TimeUnit.SECONDS);
            JSONArray list = json.getBody().getObject().getJSONArray("list");
            if(list.length()==0) {
                chat.sendMessage("No results have been found");
                return;
            }
            JSONObject item = list.getJSONObject(0);
            chat.sendMessage(String.format("Urban Dictionary " + Config.EOL + Config.EOL
                            + "Definition for **%s**: " + Config.EOL
                            + "```" + Config.EOL
                            + "%s" + Config.EOL
                            + "```" + Config.EOL
                            + "**example**: " + Config.EOL
                            + "%s" + Config.EOL + Config.EOL
                            + "_by %s (" + Emojibet.THUMBS_UP + "%s  " + Emojibet.THUMBS_DOWN + "%s)_"
                    , item.getString("word"), item.getString("definition"), item.getString("example"),
                    item.getString("author"), item.getInt("thumbs_up"), item.getInt("thumbs_down")));
        } catch (Exception ignored) {
            System.out.print(ignored.getMessage());
            ignored.printStackTrace();
        }
    }

    @Override
    public List<String> getAlias() {
        return Collections.singletonList("ud");
    }
}
