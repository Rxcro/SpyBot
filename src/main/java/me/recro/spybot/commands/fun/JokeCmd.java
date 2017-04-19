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
public class JokeCmd extends Command {
    @Override
    public void executeCommand(String[] args, MessageReceivedEvent e, MessageSender chat) {
        String jokeText="";
        jokeText = getJokeFromWeb(e.getAuthor().getName());
        e.getChannel().sendMessage(jokeText).queue();
    }

    @Override
    public List<String> getAlias() {
        return Collections.singletonList("joke");
    }

    private String getJokeFromWeb(String username) {
        try {
            URL loginurl = new URL("http://api.icndb.com/jokes/random?firstName=&lastName=" + username);
            URLConnection yc = loginurl.openConnection();
            yc.setConnectTimeout(10 * 1000);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            yc.getInputStream()));
            String inputLine = in.readLine();
            JsonParser parser = new JsonParser();
            JsonObject array = parser.parse(inputLine).getAsJsonObject();
            return array.get("value").getAsJsonObject().get("joke").getAsString();
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
}
