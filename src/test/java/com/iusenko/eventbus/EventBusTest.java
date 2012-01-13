package com.iusenko.eventbus;

import com.iusenko.eventbus.EventBus.EventHandlerHolder;
import java.util.Collection;
import static junit.framework.Assert.*;
import org.junit.Test;

/**
 *
 * @author iusenko
 */
public class EventBusTest {

    @Test(expected = IllegalArgumentException.class)
    public void testSubscribeIllegalArgument() {
        EventBus.subscribe(null);
    }

    @Test
    public void testUnsubscribeIllegalArgument() {
        EventBus.unsubscrube(null);
    }

    @Test
    public void testUnsubscribe() {
        Object handler1 = new Object() {

            @EventHandler
            public void stringHandler(String string) {
            }

            @EventHandler
            public void integerHandler(Integer integer) {
            }
        };

        Object handler2 = new Object() {

            @EventHandler
            public void booleanHandler(Boolean bool) {
            }
        };


        EventBus.subscribe(handler1);
        EventBus.subscribe(handler2);
        Collection<EventHandlerHolder> holders = EventBus.eventHandlerHolders;
        assertNotNull(holders);
        assertSame(3, holders.size());
        assertTrue(contains(holders, handler1, "stringHandler", String.class));
        assertTrue(contains(holders, handler1, "integerHandler", Integer.class));
        assertTrue(contains(holders, handler2, "booleanHandler", Boolean.class));
        EventBus.unsubscrube(handler1);
        assertNotNull(holders);
        assertSame(1, holders.size());
        assertTrue(contains(holders, handler2, "booleanHandler", Boolean.class));
        EventBus.unsubscrube(handler2);
        assertSame(0, holders.size());
    }

    @Test
    public void testSubscribe() {
        Object handler = new Object() {

            @EventHandler
            public void stringHandler(String string) {
            }

            @EventHandler
            public void integerHandler(Integer string) {
            }
        };


        EventBus.subscribe(handler);
        Collection<EventHandlerHolder> holders = EventBus.eventHandlerHolders;
        assertNotNull(holders);
        assertSame(2, holders.size());
        assertTrue(contains(holders, handler, "stringHandler", String.class));
        assertTrue(contains(holders, handler, "integerHandler", Integer.class));
    }

    private boolean contains(Collection<EventHandlerHolder> holders, Object reference,
            String methodName, Class eventClass) {
        for (EventHandlerHolder holder : holders) {
            if (holder.getListenerReference() == reference && holder.getEventClass() == eventClass
                    && holder.getListenerMethod().getName().equals(methodName)) {
                return true;
            }

        }
        return false;
    }
}
