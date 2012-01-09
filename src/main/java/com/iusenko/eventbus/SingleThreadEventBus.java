package com.iusenko.eventbus;

/**
 *
 * @author iusneko
 */
final class SingleThreadEventBus /*extends EventBus implements Runnable*/ {
//
//    private final List<Object> events = new ArrayList<Object>();
//    private boolean running = true;
//
//    public SingleThreadEventBus() {
//        Thread thread = new Thread(this);
//        thread.start();
//
//    }
//
//    @Override
//    public void publish(Object event) {
//        if (event == null) {
//            throw new IllegalArgumentException();
//        }
//
//        synchronized (events) {
//            events.add(event);
//            events.notify();
//        }
//    }
//
//    private void dispatchEvent(Object event) {
//        for (EventHandlerHolder holder : getEventHandlerHolders()) {
//            // run handler in current thread
//            if (holder.getEventClass() == event.getClass()) {
//                try {
//                    holder.getMethod().invoke(holder.getReference(), event);
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//            }
//        }
//    }
//
//    @Override
//    public void run() {
//        while (true) {
//            try {
//                if (events.size() > 0) {
//                    Object event = popEvent();
//                    dispatchEvent(event);
//                } else {
//                    synchronized (events) {
//                        events.wait();
//                    }
//                }
//
//                if (!running) {
//                    return;
//                }
//
//            } catch (InterruptedException ex) {
//                running = false;
//                return;
//            }
//        }
//    }
//
//    protected final synchronized Object popEvent() {
//        Object event = events.get(0);
//        events.remove(0);
//        return event;
//    }
//
//    public void stopEventBus() {
//        this.running = false;
//        synchronized (events) {
//            events.notify();
//        }
//    }
}
