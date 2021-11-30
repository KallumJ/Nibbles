package team.bits.nibbles.event.base;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;

record EventInfo(@NotNull Class<? extends Event> eventClass,
                 @NotNull Class<? extends EventListener> eventListenerClass,
                 @NotNull Method eventListenerMethod) {
}
