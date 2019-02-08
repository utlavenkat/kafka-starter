package org.venkat.kafka.clients.producers;

import org.venkat.kafka.producers.MyTopicProducer;

public class ProducerDemo {

    public static void main(String[] args) {
        MyTopicProducer.send("This is my first message after refactoring");
    }
}
