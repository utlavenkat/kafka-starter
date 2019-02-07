package org.venkat.kafka.consumers;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;

public class ConsumerAssignAndSeekDemo {
    private static final Logger logger = LoggerFactory.getLogger("ConsumerAssignAndSeekDemo");

    public static void main(String[] args) {

        //create consumer properties
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"127.0.0.1:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");

        //create consumer
        Consumer<String,String> consumer = new KafkaConsumer<>(properties);

        //assign Topic partition
        TopicPartition topicPartition = new TopicPartition("my_topic",0);
        consumer.assign(Collections.singletonList(topicPartition));
        consumer.seek(topicPartition,3);

        boolean processNextMessage = true;
        int totalMessagesToBeProcessed = 5;
        int messagesProcessed = 0;
        while (processNextMessage) {
            ConsumerRecords<String,String> consumerRecords = consumer.poll(Duration.ofMillis(1000));
            for (ConsumerRecord consumerRecord:consumerRecords) {
                logger.info("Key::"+consumerRecord.key()+"\t Message::"+consumerRecord.value() +"\t Offset::"+consumerRecord.offset());
                messagesProcessed++;
                if (messagesProcessed == totalMessagesToBeProcessed) {
                    processNextMessage = false;
                    break;
                }
            }
        }
    }
}
