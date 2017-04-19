package me.recro.spybot.commands.fun;


import me.recro.spybot.Config;
import me.recro.spybot.commands.Command;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Admin on 4/16/2017.
 */
public class FMLCommand extends Command {

    private static final int MIN_QUEUE_ITEMS = 40;
    private BlockingQueue<String> items = null;

    @Override
    public void executeCommand(String[] args, MessageReceivedEvent e, MessageSender chat) {

        items = new LinkedBlockingQueue<>();

        if(items.size() < MIN_QUEUE_ITEMS) {
            getFMLItems();
        }
        if(!items.isEmpty()) {
            try {
                String item = StringEscapeUtils.unescapeHtml4(items.take());
                if(item.length() >= 2000) {
                    item = item.substring(0, 1999);
                }
                e.getChannel().sendMessage(item).queue();
            } catch (InterruptedException ex) {
//                Launcher.logToDiscord(e, "fml-command", "interrupted");
            }
        }
    }

    @Override
    public List<String> getAlias() {
        return Collections.singletonList("fml");
    }

    private void getFMLItems() {
        try {
            Document document = Jsoup.connect("http://fmylife.com/random").timeout(30_000).userAgent(Config.USER_AGENT).get();
            if (document != null) {
                Elements fmls = document.select("p.block a[href^=/article/]");
                for (Element fml : fmls) {
                    items.add(fml.text().trim());
                }
            }
        } catch (IOException e) {
//            Launcher.logToDiscord(e, "fml-command", "boken");
        }
    }
}
