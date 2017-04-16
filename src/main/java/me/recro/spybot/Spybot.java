package me.recro.spybot;


import me.recro.spybot.commands.fun.TestCmd;
import me.recro.spybot.music.MusicCommand;
import me.recro.spybot.music.PlayCmd;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import javax.security.auth.login.LoginException;

public final class Spybot  {

    public static final String discordToken="MzAxNDcyMjQ2Mzk0OTEyNzgx.C9AKhQ.xgJUFdsNo8MCBF_Ip17pOISVgM4";
    public static void main (String[] args) {
        JDA discord = null;
        try {
            discord = new JDABuilder(AccountType.BOT).setToken(discordToken).buildBlocking();
            discord.addEventListener(new TestCmd());
            discord.addEventListener(new MusicCommand());
            discord.addEventListener(new PlayCmd());
        } catch (LoginException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RateLimitedException e) {
            e.printStackTrace();
        }
//        discord.addEventListener(new TestCmd());
//        discord.addEventListener(new CoinflipCmd());
//        discord.addEventListener(new ClearCmd());
    }

}
