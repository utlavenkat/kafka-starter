package org.venkat.kafka.producers;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class ProducerDemo {

    public static void main(String[] args) {
        //Create ProducerDemo Config
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"127.0.0.1:9092");

        //Create producer and producer record
        Producer<String,String> producer = new KafkaProducer<>(properties);
        ProducerRecord<String,String> producerRecord = new ProducerRecord<>("my_topic","My message from java producer");

        //Send the data
        producer.send(producerRecord);

        //Flush the data
        producer.flush();

        //Flush and close the producer
        producer.close();
    }
}
