package org.venkat.kafka.clients.producers;

import org.venkat.kafka.producers.MyTopicProducer;

public class ProducerCallbackDemo {
    public static void main(String[] args) {
        MyTopicProducer.sendWithCallback("This is my first message for callback after refactoring");
    }
}
