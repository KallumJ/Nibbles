package team.bits.nibbles.event.base;

import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class EventManager {

    public static final EventManager INSTANCE = new EventManager();

    private final Map<Class<? extends Event>, EventContainer> eventContainers = new HashMap<>();

    private EventManager() {
    }

    public void registerEvents(@NotNull EventListener listenerImpl) {
        for (Class<? extends EventListener> listenerInterface
                : InternalEventUtils.getEventListenerInterfaces(listenerImpl.getClass())) {
            EventContainer container = this.findOrCreateEventContainer(listenerInterface);
            container.addListener(listenerImpl);
        }
    }

    public void unregisterEvents(@NotNull EventListener listenerImpl) {
        for (EventContainer container : this.eventContainers.values()) {
            container.removeListener(listenerImpl);
        }
    }

    public void fireEvent(@NotNull Event event) {
        Validate.notNull(event, "Event cannot be null");

        EventContainer eventContainer = this.eventContainers.get(event.getClass());
        if (eventContainer != null) {

            Method eventListenerMethod = eventContainer.eventInfo.eventListenerMethod();
            for (EventListener listener : eventContainer.getListeners()) {
                try {
                    eventListenerMethod.invoke(listener, event);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    throw new InternalEventException(
                            String.format("Failed to dispatch event %s@%d to listener %s@%d",
                                    event.getClass().getName(), System.identityHashCode(listener),
                                    listener.getClass().getName(), System.identityHashCode(listener)
                            ), ex
                    );
                }
            }
        }
    }

    private @NotNull EventContainer findOrCreateEventContainer(@NotNull Class<? extends EventListener> listenerInterface) {
        EventInfo eventInfo = InternalEventUtils.createEventInfo(listenerInterface);
        for (EventContainer container : this.eventContainers.values()) {
            if (container.eventInfo.eventClass() == eventInfo.eventClass()) {
                return container;
            }
        }

        EventContainer container = new EventContainer(eventInfo);
        this.eventContainers.put(eventInfo.eventClass(), container);
        return container;
    }

    private static class EventContainer {

        private final Set<EventListener> listeners = new HashSet<>();

        private final EventInfo eventInfo;

        private EventContainer(@NotNull EventInfo eventInfo) {
            this.eventInfo = Objects.requireNonNull(eventInfo);
        }

        public void addListener(@NotNull EventListener listener) {
            this.listeners.add(Objects.requireNonNull(listener));
        }

        public void removeListener(@NotNull EventListener listener) {
            this.listeners.remove(Objects.requireNonNull(listener));
        }

        public @NotNull @UnmodifiableView Set<EventListener> getListeners() {
            return Collections.unmodifiableSet(this.listeners);
        }
    }
}
