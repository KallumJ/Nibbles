package team.bits.nibbles;

import net.fabricmc.api.ModInitializer;
import team.bits.nibbles.command.CommandManager;
import team.bits.nibbles.event.base.EventManager;
import team.bits.nibbles.event.server.ServerTickEvent;
import team.bits.nibbles.utils.Scheduler;

public class Nibbles implements ModInitializer {

    @Override
    public void onInitialize() {
        EventManager.INSTANCE.registerEvents((ServerTickEvent.Listener) event -> Scheduler.tick());

        EventManager.INSTANCE.registerEvents(CommandManager.INSTANCE);
    }
}
