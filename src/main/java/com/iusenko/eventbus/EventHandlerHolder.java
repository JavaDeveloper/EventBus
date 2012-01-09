package com.iusenko.eventbus;

import java.lang.reflect.Method;

/**
 *
 * @author iusenko
 */
final class EventHandlerHolder {

    private final Object reference;
    private final Method method;
    private final Class eventClass;

    public EventHandlerHolder(Object reference, Method method, Class eventClass) {
        this.reference = reference;
        this.method = method;
        this.eventClass = eventClass;
    }

    public Class getEventClass() {
        return eventClass;
    }

    public Method getMethod() {
        return method;
    }

    public Object getReference() {
        return reference;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(reference.getClass().getName());
        sb.append("#").append(method.getName()).append("(").append(eventClass).append(")");
        return sb.toString();
    }
}
