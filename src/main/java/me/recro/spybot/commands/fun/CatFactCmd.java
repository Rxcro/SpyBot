package me.recro.spybot.commands.fun;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.recro.spybot.commands.Command;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Admin on 4/16/2017.
 */
public class CatFactCmd extends Command {


    public static String getCatFact() {
        try {
            URL loginurl = new URL("http://catfacts-api.appspot.com/api/facts");
            URLConnection yc = loginurl.openConnection();
            yc.setConnectTimeout(10 * 1000);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            yc.getInputStream()));
            String inputLine = in.readLine();
            JsonParser parser = new JsonParser();
            JsonObject array = parser.parse(inputLine).getAsJsonObject();
            return ":cat:  " + array.get("facts").getAsString();
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    @Override
    public void executeCommand(String[] args, MessageReceivedEvent e, MessageSender chat) {
        String catFaction = getCatFact();
        if(catFaction!=null) {
            e.getChannel().sendMessage(catFaction).queue();
            return;
        }
        e.getChannel().sendMessage("The fur balls clogged up our systems and couldn't find any catfacts, try again in a few minutes").queue();
    }

    @Override
    public List<String> getAlias() {
        return Collections.singletonList("catfact");
    }
}
