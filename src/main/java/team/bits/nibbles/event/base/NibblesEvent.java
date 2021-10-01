package team.bits.nibbles.event.base;

import net.fabricmc.fabric.api.event.Event;

public abstract class NibblesEvent<T> extends Event<T> {

	/**
	 * Unregisters a listener from the event.
	 *
	 * @param listener The desired listener.
	 */
	public abstract void unregister(T listener);
}
