package org.venkat.kafka.clients.producers;

import org.venkat.kafka.producers.MyTopicProducer;

public class ProducerWithKeyDemo {

    public static void main(String[] args){
        String key;
        for (int i = 0; i<10; i++) {
            key = "_id"+i;
            MyTopicProducer.send(key,"my message"+i);
        }
    }
}
