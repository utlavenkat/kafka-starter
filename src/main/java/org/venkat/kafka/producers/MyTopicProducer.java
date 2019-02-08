package org.venkat.kafka.producers;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.venkat.kafka.Constants;
import org.venkat.kafka.properties.PropertiesLoader;

import java.util.Date;
import java.util.Properties;

public class MyTopicProducer {

    private static Logger logger = LoggerFactory.getLogger("MyTopicProducer");
    private static  Producer<String,String> producer = null;
    private static final String TOPIC_NAME = PropertiesLoader.getPropertyValue(Constants.TOPIC_NAME_MY_TOPIC);

    private MyTopicProducer() {
    }

    private static void createProducer() {
        final Properties properties = new Properties();
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, PropertiesLoader.getPropertyValue(Constants.BOOTSTRAP_SERVER));

        producer = new KafkaProducer<>(properties);
    }

    public static boolean send(String message) {
        createProducer();
        ProducerRecord<String,String> producerRecord = new ProducerRecord<>(TOPIC_NAME,message);
        //Send the data
        producer.send(producerRecord);

        //Flush the data
        producer.flush();

        //Flush and close the producer
        producer.close();

        return true;
    }

    public static boolean sendWithCallback(String message) {
        createProducer();
        ProducerRecord<String,String> producerRecord = new ProducerRecord<>(TOPIC_NAME,message);
        //Send the data
        producer.send(producerRecord, (recordMetadata, e) -> processResult(null, recordMetadata, e));

        //Flush the data
        producer.flush();

        //Flush and close the producer
        producer.close();
        return true;
    }

    public static boolean send(String key,String message) {
        createProducer();
        ProducerRecord<String,String> producerRecord = new ProducerRecord<>(TOPIC_NAME,key,message);
        //Send the data
        producer.send(producerRecord, (recordMetadata, e) -> processResult(key, recordMetadata, e));

        //Flush the data
        producer.flush();

        //Flush and close the producer
        producer.close();

        return true;
    }

    private static void processResult(String key, RecordMetadata recordMetadata, Exception e) {
        if (e == null) {
            logger.info("Message was processed successfully.  Details are mentioned below \n");
            logger.info("Key::"+key);
            logger.info("Topic::" + recordMetadata.topic());
            logger.info("Partition::" + recordMetadata.partition());
            logger.info("Offset::" + recordMetadata.offset());
            logger.info("Date and Time::" + new Date(recordMetadata.timestamp()));
        }
        else {
            logger.error("Error occurred while producing the message to topic. \n");
            logger.error("Error details are "+ e);
        }
    }

}
