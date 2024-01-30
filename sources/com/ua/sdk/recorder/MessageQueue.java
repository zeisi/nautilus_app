package com.ua.sdk.recorder;

import java.util.ArrayDeque;

public class MessageQueue {
    private ArrayDeque<Message> queue = new ArrayDeque<>(1024);

    public synchronized void offer(Message message) {
        this.queue.offer(message);
        notify();
    }

    public synchronized Message poll() {
        return this.queue.poll();
    }
}
