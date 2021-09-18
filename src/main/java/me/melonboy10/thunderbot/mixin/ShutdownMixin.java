package me.melonboy10.thunderbot.mixin;


import me.melonboy10.thunderbot.ThunderBotMod;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerTask;
import net.minecraft.server.dedicated.command.StopCommand;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class ShutdownMixin {

    @Inject(method = "stop", at = @At(value = "HEAD"))
    private void onStop(boolean bl, CallbackInfo ci) {
        ThunderBotMod.jda.shutdown();
        System.out.println("Shutting down Bot");
    }

}
