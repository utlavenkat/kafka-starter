package org.venkat.kafka.clients.consumers;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class ConsumerDemo {
    private static Logger logger = LoggerFactory.getLogger(ConsumerDemo.class);

    public static void main(String[] args) {

        //Create consumer configs
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"127.0.0.1:9092");
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG,"my-group-1");
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");

        //Create consumer
        Consumer<String,String> consumer = new KafkaConsumer<>(properties);

        //subscribe consumer to our topics
        consumer.subscribe(Collections.singleton("my_topic"));

        //poll for new data
        while (true) {
            ConsumerRecords<String,String> consumerRecords = consumer.poll(Duration.ofMillis(1000L));
            consumerRecords.forEach(consumerRecord -> logger.info("Topic::"+consumerRecord.topic() +"\t Partition::"+consumerRecord.partition() +"\t "+consumerRecord.offset() +"\t Message::"+consumerRecord.value()));
        }
    }
}
