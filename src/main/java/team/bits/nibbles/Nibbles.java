package team.bits.nibbles;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import team.bits.nibbles.command.CommandManager;

public class Nibbles implements ModInitializer {

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register(CommandManager.INSTANCE);
    }
}
