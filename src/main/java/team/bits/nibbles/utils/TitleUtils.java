package team.bits.nibbles.utils;

import net.minecraft.network.packet.s2c.play.*;
import net.minecraft.server.network.*;
import net.minecraft.text.*;
import org.jetbrains.annotations.*;

public final class TitleUtils {

    private TitleUtils() {
    }

    public static void showTitle(@NotNull ServerPlayerEntity player,
                                 @NotNull Text title, @NotNull Text subtitle,
                                 int fadeIn, int stay, int fadeOut) {

        if (fadeIn + stay + fadeOut <= 0) {
            throw new IllegalArgumentException("Total title duration must be at least 1 tick");
        }

        Title titleObject = new Title(title, subtitle);
        Times timesObject = new Times(fadeIn, stay, fadeOut);

        titleObject.send(player);
        timesObject.send(player);
    }

    public record Title(@NotNull Text title, @NotNull Text subtitle) {

        public void send(@NotNull ServerPlayerEntity player) {
            player.networkHandler.sendPacket(new TitleS2CPacket(this.title()));
            player.networkHandler.sendPacket(new SubtitleS2CPacket(this.subtitle()));
        }
    }

    public record Times(int fadeIn, int stay, int fadeOut) {

        public void send(@NotNull ServerPlayerEntity player) {
            player.networkHandler.sendPacket(new TitleFadeS2CPacket(this.fadeIn(), this.stay(), this.fadeOut()));
        }
    }
}
