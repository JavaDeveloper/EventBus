package com.iusenko.eventbus;

/**
 *
 * @author iusneko
 */
public class Main {

    public static void main(String[] argv) throws InterruptedException {
        EventBus.subscribe(handler);
        EventBus.publish("hello");
        EventBus.publish(new Integer(3));
        EventBus.publish(Boolean.TRUE);// thre is no handler for this type
        EventBus.unsubscrube(handler);
        EventBus.publish("hello"); // handler has been unregistered
    }
    static Object handler = new Object() {

        @EventHandler
        public void stringHandler(String string) {
            System.err.println("string handler: " + string);
        }

        @EventHandler
        public void integerHandler(Integer integer) {
            System.err.println("integer handler: " + integer);
        }
    };
}
