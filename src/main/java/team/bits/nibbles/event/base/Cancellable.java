package team.bits.nibbles.event.base;

public interface Cancellable {

    void setCancelled(boolean cancelled);

    boolean isCancelled();
}
