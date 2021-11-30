package team.bits.nibbles.event.base;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

final class InternalEventUtils {

    private InternalEventUtils() {
    }

    /**
     * Returns a set of all interfaces implemented by the given class which directly
     * extend from {@link EventListener}.
     */
    public static @NotNull @UnmodifiableView Set<Class<? extends EventListener>> getEventListenerInterfaces(
            @NotNull Class<? extends EventListener> listenerClass) {

        Set<Class<? extends EventListener>> eventListenerInterfaces = new HashSet<>();

        // we loop over all interfaces implemented by this class, and look for all
        // interfaces which directly extend from the EventListener class.
        for (Class<?> implementedInterface : getInterfacesRecursive(listenerClass)) {
            Class<?>[] allSuperInterfaces = implementedInterface.getInterfaces();
            if (allSuperInterfaces.length > 0) {
                for (Class<?> superInterface : allSuperInterfaces) {
                    if (superInterface == EventListener.class) {
                        //noinspection unchecked
                        eventListenerInterfaces.add((Class<? extends EventListener>) implementedInterface);
                        break;
                    }
                }
            }
        }

        return eventListenerInterfaces;
    }

    /**
     * Recursively find all interfaces which are implemented by a given class.
     */
    private static @NotNull @UnmodifiableView Set<Class<?>> getInterfacesRecursive(@NotNull Class<?> baseClass) {
        Set<Class<?>> allInterfaces = new HashSet<>();

        // loop over all the interfaces of this class
        for (Class<?> childInterface : baseClass.getInterfaces()) {

            // add the interface to our set
            allInterfaces.add(childInterface);
            // recursively add all interfaces of this child
            allInterfaces.addAll(getInterfacesRecursive(childInterface));

            // check if this class extends from a superclass
            Class<?> superClass = baseClass.getSuperclass();
            if (superClass != null) {
                // recursively add all interfaces of the superclass
                allInterfaces.addAll(getInterfacesRecursive(superClass));
            }
        }

        return Collections.unmodifiableSet(allInterfaces);
    }

    /**
     * Create an {@link EventInfo} record from an event listener interface. This will
     * find the {@link Event} class handled by this listener, and the method to call
     * the event.
     */
    public static @NotNull EventInfo createEventInfo(@NotNull Class<? extends EventListener> eventListenerInterface) {

        // find all method in the given class
        Method[] allListenerMethods = eventListenerInterface.getMethods();
        // if there's more than one method, throw an exception
        if (allListenerMethods.length != 1) {
            throw new InternalEventException(
                    String.format("Event listener %s must have a single method", eventListenerInterface)
            );
        }

        // get the first method
        Method eventListenerMethod = allListenerMethods[0];
        // if the method doesn't return void, throw an exception
        if (eventListenerMethod.getReturnType() != Void.TYPE) {
            throw new InternalEventException(
                    String.format("Event listener method in %s must return void", eventListenerInterface)
            );
        }

        // get all parameters from the method
        Class<?>[] eventListenerMethodParameters = eventListenerMethod.getParameterTypes();
        // if there is more than one parameter, throw an exception
        if (eventListenerMethodParameters.length != 1) {
            throw new InternalEventException(
                    String.format(
                            "Event listener %s.%s() should have a single argument 'event'",
                            eventListenerInterface.getName(), eventListenerMethod.getName()
                    )
            );
        }

        // get the first parameter
        Class<?> eventParameter = eventListenerMethodParameters[0];
        // check if the parameter inherits from Event
        Class<? extends Event> eventClass = null;
        for (Class<?> cInterface : getInterfacesRecursive(eventParameter)) {
            if (cInterface == Event.class) {
                //noinspection unchecked
                eventClass = (Class<? extends Event>) eventParameter;
                break;
            }
        }
        // if the parameter does not inherit from Event, throw an exception
        if (eventClass == null) {
            throw new InternalEventException(
                    String.format(
                            "Event listener %s.%s() should have a single argument of type %s",
                            eventListenerInterface.getName(), eventListenerMethod.getName(), Event.class.getName()
                    )
            );
        }

        return new EventInfo(eventClass, eventListenerInterface, eventListenerMethod);
    }
}
