package me.melonboy10.thunderbot.mixin;

import me.melonboy10.thunderbot.ThunderBotMod;
import me.melonboy10.thunderbot.events.ChatEvent;
import net.dv8tion.jda.api.entities.ISnowflake;
import net.dv8tion.jda.api.entities.User;
import net.fabricmc.fabric.api.biome.v1.BiomeModificationContext;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashSet;

@Mixin(LightningEntity.class)
public class WeatherMixin {

    @Inject(method = "<init>", at = @At("TAIL"))
    private void lightingCreate(EntityType<? extends LightningEntity> entityType, World world, CallbackInfo ci) {
        StringBuilder mentions = new StringBuilder();
        for (User user : ChatEvent.users) {
            mentions.append(user.getAsMention());
        }
        ChatEvent.channel.sendMessage(
            "Gather round fellow Comrades! We've got a thunderin' horizon! " + mentions).queue();

        ChatEvent.users.clear();
        try {
            ThunderBotMod.CONFIG.setValue("users", "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
