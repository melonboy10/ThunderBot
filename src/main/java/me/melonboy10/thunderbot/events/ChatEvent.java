package me.melonboy10.thunderbot.events;

import me.melonboy10.thunderbot.SimpleConfig;
import me.melonboy10.thunderbot.ThunderBotMod;
import net.dv8tion.jda.api.entities.ISnowflake;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;

public class ChatEvent extends ListenerAdapter {

    public static TextChannel channel;
    public static LinkedHashSet<User> users = new LinkedHashSet<>();

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        if (!event.getMember().getUser().isBot()) {
            if (event.getMessage().getContentRaw().contains("*setchat")) {
                event.getChannel().sendMessage("This is now the channel where people will get notified").queue();
                channel = event.getChannel();
                try {
                    ThunderBotMod.CONFIG.setValue("channel", channel.getId());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else  if (event.getMessage().getContentRaw().contains("*notifyme")) {
                event.getChannel().sendMessage("You will be notified, " + event.getMember().getUser().getAsMention() + "!").queue();
                users.add(event.getMember().getUser());

                try {
                    ThunderBotMod.CONFIG.setValue("users", Arrays.toString(users.stream().map(ISnowflake::getIdLong).toArray()).replaceAll("[\\[\\] ]", ""));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
