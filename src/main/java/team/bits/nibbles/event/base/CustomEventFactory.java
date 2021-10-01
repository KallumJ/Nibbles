package team.bits.nibbles.event.base;

import net.fabricmc.fabric.api.event.Event;

import java.util.Collection;
import java.util.function.Function;

public final class CustomEventFactory {

    /**
     * Create a "collection-backed" Event instance. The main difference between
     * this and an {@linkplain net.fabricmc.fabric.impl.base.event.ArrayBackedEvent "array-backed"}
     * Event instance is that this allows for easy unregistering of listeners. Note that the
     * "array-backed" Event instance has better performance than this one.
     *
     * @param type           The listener class type.
     * @param invokerFactory The invoker factory, combining multiple listeners into one instance.
     * @param <T>            The listener type.
     * @return The Event instance.
     * @see net.fabricmc.fabric.api.event.EventFactory#createArrayBacked(Class, Function)
     */
    public static <T> NibblesEvent<T> createCollectionBacked(Class<? super T> type, Function<Collection<T>, T> invokerFactory) {
        return new CollectionBackedEvent<>(type, invokerFactory);
    }
}
