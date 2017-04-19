package me.recro.spybot.commands.fun;

import com.sun.org.apache.xalan.internal.xsltc.compiler.Template;
import me.recro.spybot.Config;
import me.recro.spybot.commands.Command;
import me.recro.spybot.modules.reddit.RedditScraper;
import me.recro.spybot.modules.reddit.pojo.Image;
import me.recro.spybot.modules.reddit.pojo.ImagePreview;
import me.recro.spybot.modules.reddit.pojo.Post;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.utils.PermissionUtil;
import org.apache.commons.lang3.StringEscapeUtils;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

/**
 * Created by Admin on 4/16/2017.
 */
public class RedditCommand extends Command {

    private static Set<String> whitelistedDomains = new HashSet<>(Arrays.asList(new String[]{
            "imgur.com",
            "i.imgur.com",
            "i.redd.it",
            "pbs.twimg.com",
            "gfycat.com",
            "file1.answcdn.com",
            "i.reddituploads.com",
            "youtube.com"
    }));

    @Override
    public void executeCommand(String[] args, MessageReceivedEvent e, MessageSender chat) {
        String subReddit = "funny";
        if(args.length>0) {
            subReddit=args[0];
        }
        List<Post> dailyTop = RedditScraper.getDailyTop(subReddit);
        Random rng = new Random();
        Post post;
        ImagePreview preview = null;

    }

    @Override
    public List<String> getAlias() {
        return Collections.singletonList("reddit");
    }
}
