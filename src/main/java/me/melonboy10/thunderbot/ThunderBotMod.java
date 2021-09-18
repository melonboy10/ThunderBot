package me.melonboy10.thunderbot;

import me.melonboy10.thunderbot.events.ChatEvent;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.*;
import net.fabricmc.api.ModInitializer;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Scanner;

public class ThunderBotMod implements ModInitializer {

	public static final SimpleConfig CONFIG = SimpleConfig.of( "config" ).provider( ThunderBotMod::provider ).request();
	public static JDA jda;

	public final String KEY = CONFIG.getOrDefault( "key", null);
	public final String USERS = CONFIG.getOrDefault( "users", "");
	public final String CHANNEL = CONFIG.getOrDefault( "channel", "");

	@Override
	public void onInitialize() {
		System.out.println("Loaded Thunder Mod");

		try {
			jda = JDABuilder.createDefault(KEY).build().awaitReady();
			jda.addEventListener(new ChatEvent());

			ChatEvent.channel = jda.getTextChannelById(CHANNEL);

			if (USERS != null && !USERS.equals("null") && !USERS.isBlank()) {
				if (USERS.contains(",")) {
					ChatEvent.users.addAll(Arrays.stream(USERS.split(",")).map(User::fromId).toList());
				} else {
					ChatEvent.users = new LinkedHashSet<User>(){{add(User.fromId(USERS));}};
				}
			}
		} catch (LoginException | InterruptedException e) {
			System.out.println("Failed to load bot!");
			e.printStackTrace();
		}
	}



	private static String provider(String filename) {
		return """
			#Default Config
			key=null
			channel=null
			users=null""";
	}
}
