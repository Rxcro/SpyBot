package me.recro.spybot.commands;

import me.recro.spybot.Utils.Info;
import me.recro.spybot.Utils.MessageUtil;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.List;

/**
 * Created by logan on 4/14/17.
 */
public abstract class Command extends ListenerAdapter {

    public abstract void executeCommand(String[] args, MessageReceivedEvent e, MessageSender chat);

    public abstract List<String> getAlias();

    public boolean allowsPrivate() {
        return false;
    }

    public boolean authorExclusive() {
        return false;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        // Checks related to the Event's objects, to prevent concurrency issues.
        if (e.getAuthor() == null || e.getChannel() == null)
            return;

        if (e.getAuthor().isBot() || !isValidCommand(e.getMessage()))
            return; // Ignore message if it's not a command or sent by a bot
        if (authorExclusive() && !e.getAuthor().getId().equals(Info.AUTHOR_ID))
            return; // Ignore if the command is meant to be used by the owner only
        if (e.isFromType(ChannelType.TEXT) && MessageUtil.canNotTalk(e.getTextChannel()))
            return; // Ignore if we cannot talk in the channel anyway

        String[] args = commandArgs(e.getMessage());
        MessageSender chat = new MessageSender(e);

        if (e.isFromType(ChannelType.PRIVATE) && !allowsPrivate()) { // Check if the command is guild-only
            chat.sendMessage("**This command can only be used in a guild!**");
        } else {
            try {
                executeCommand(args, e, chat);
            } catch (Exception ex) {
                ex.printStackTrace();
                String msg = "User: **" + MessageUtil.userDiscrimSet(e.getAuthor())
                        + "**\nMessage:\n*" + MessageUtil.stripFormatting(e.getMessage().getContent());
//                        + "*\n\nStackTrace:```java\n" + ExceptionUtils.getStackTrace(ex) + "```";
                if (msg.length() <= 2000) {
                    chat.sendPrivateMessageToUser(msg, e.getJDA().getUserById(Info.AUTHOR_ID));
                }
            }
        }

    }

    private boolean isValidCommand(Message msg) {
        String prefix = Info.PREFIX;
        if (!msg.getRawContent().startsWith(prefix))
            return false; // It's not a command if it doesn't start with our prefix
        String cmdName = msg.getRawContent().substring(prefix.length());
        if (cmdName.contains(" ")) {
            cmdName = cmdName.substring(0, cmdName.indexOf(" ")); // If there are parameters, remove them
        }
        if (cmdName.contains("\n")) {
            cmdName = cmdName.substring(0, cmdName.indexOf("\n"));
        }
        return getAlias().contains(cmdName.toLowerCase());
    }

    private String[] commandArgs(Message msg) {
        String noPrefix = msg.getRawContent().substring(Info.PREFIX.length());
        if (!noPrefix.contains(" ")) { // No whitespaces -> No args
            return new String[]{};
        }
        return noPrefix.substring(noPrefix.indexOf(" ") + 1).split("\\s+");
    }

    public class MessageSender {
        public final MessageReceivedEvent event;

        MessageSender(MessageReceivedEvent event) {
            this.event = event;
        }

        void sendMessage(String msgContent, MessageChannel tChannel) {
            if (tChannel == null) return;
            MessageUtil.sendMessage(msgContent, tChannel);
        }

        public void sendMessage(String msgContent) {
            sendMessage(msgContent, event.getChannel());
        }

        public void sendEmbed(String title, String description) {
            if (event.isFromType(ChannelType.TEXT) && event.getGuild().getSelfMember().hasPermission(event.getTextChannel(), Permission.MESSAGE_EMBED_LINKS)) {
                MessageUtil.sendMessage(new EmbedBuilder().setTitle(title, null).setDescription(description).build(), event.getChannel());
            } else {
                sendMessage("Please give the bot permissions to `EMBED LINKS`.");
            }
        }

        void sendPrivateMessageToUser(String content, User user) {
            user.openPrivateChannel().queue(c -> sendMessage(content, c));
        }
    }
}
