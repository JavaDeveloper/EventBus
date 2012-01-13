package com.iusenko.eventbus;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 *
 * @author iusenko
 */
public class EventBus {

    protected static final Collection<EventHandlerHolder> eventHandlerHolders = new ArrayList<EventHandlerHolder>();

    public static void subscribe(Object handler) {
        if (handler == null) {
            throw new IllegalArgumentException();
        }
        Method[] methods = handler.getClass().getDeclaredMethods();
        for (int i = 0; methods != null && i < methods.length; i++) {
            Method method = methods[i];
            if (method.isAnnotationPresent(EventHandler.class)) {
                Class[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length == 1) {
                    Class parameterClass = parameterTypes[0];
                    EventHandlerHolder holder = new EventHandlerHolder(handler, method, parameterClass);
                    eventHandlerHolders.add(holder);
                }
            }
        }
    }

    public static void unsubscrube(Object handler) {
        if (handler == null) {
            return;
        }
        for (Iterator<EventHandlerHolder> it = eventHandlerHolders.iterator(); it.hasNext();) {
            EventHandlerHolder holder = it.next();
            if (holder.getListenerReference() == handler) {
                it.remove();
            }
        }
    }

    public static void publish(Object event) {
        if (event == null) {
            throw new IllegalArgumentException();
        }

        for (EventHandlerHolder holder : eventHandlerHolders) {
            // run separate thread per handler
            if (holder.getEventClass() == event.getClass()) {
                new HandlerThread(holder, event).start();
            }
        }
    }

    private static class HandlerThread extends Thread {

        private final EventHandlerHolder holder;
        private final Object event;

        public HandlerThread(EventHandlerHolder holder, Object event) {
            setName(holder.toString());
            this.holder = holder;
            this.event = event;
        }

        @Override
        public void run() {
            try {
                holder.getListenerMethod().invoke(holder.getListenerReference(), event);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Helper class which binds event class with appropriate handler method.
     */
    static final class EventHandlerHolder {

        /**
         * Reference of the class which contains handler methods.
         */
        private final Object listenerReference;
        /**
         * Method of the
         * <code>listenerReference</code> which is marked by
         * <code>EventHandler</code> annotation.
         *
         * @see EventHandler
         */
        private final Method listenerMethod;
        /**
         * Class type of the event.
         */
        private final Class eventClass;

        public EventHandlerHolder(Object listenerReference, Method listenerMethod, Class eventClass) {
            this.listenerReference = listenerReference;
            this.listenerMethod = listenerMethod;
            this.eventClass = eventClass;
        }

        public Class getEventClass() {
            return eventClass;
        }

        public Method getListenerMethod() {
            return listenerMethod;
        }

        public Object getListenerReference() {
            return listenerReference;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder(listenerReference.getClass().getName());
            sb.append("#").append(listenerMethod.getName()).append("(").append(eventClass).append(")");
            return sb.toString();
        }
    }
}
