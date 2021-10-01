package team.bits.nibbles.event.base;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;

class CollectionBackedEvent<T> extends NibblesEvent<T> {

    private final Function<Collection<T>, T> invokerFactory;
    private final Lock lock = new ReentrantLock();
    private final Collection<T> handlers = new LinkedList<>();

    CollectionBackedEvent(Class<? super T> type, Function<Collection<T>, T> invokerFactory) {
        this.invokerFactory = invokerFactory;
        this.update();
    }

    void update() {
        this.invoker = this.invokerFactory.apply(this.handlers);
    }

    @Override
    public void register(T listener) {
        Objects.requireNonNull(listener, "Tried to register a null listener!");

        this.lock.lock();

        try {
            this.handlers.add(listener);
            update();
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public void unregister(T listener) {
        Objects.requireNonNull(listener, "Tried to unregister a null listener!");

        this.lock.lock();

        try {
            this.handlers.remove(listener);
            update();
        } finally {
            this.lock.unlock();
        }
    }
}
