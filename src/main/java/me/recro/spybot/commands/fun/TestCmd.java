package me.recro.spybot.commands.fun;

import me.recro.spybot.commands.Command;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.Collections;
import java.util.List;

/**
 * Created by Admin on 4/11/2017.
 */
public class TestCmd extends Command {

    @Override
    public void executeCommand(String[] args, MessageReceivedEvent e, MessageSender chat) {
        String name = e.getMember().getEffectiveName();
        String onename = e.getMember().getAsMention();
        String response = onename + " I'll test your mums bed out tonight, faggot";
        e.getTextChannel().sendMessage(response).queue();
        e.getChannel().getJDA().getPresence().setGame(Game.of("in your mums bed, faggot"));
    }

    @Override
    public List<String> getAlias() {
        return Collections.singletonList("test");
    }
}
