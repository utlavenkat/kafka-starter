package org.venkat.kafka.producers;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Properties;

public class ProducerCallbackDemo {

    private static Logger logger = LoggerFactory.getLogger("ProducerCallbackDemo");

    public static void main(String[] args) {


            //Create ProducerDemo Config
            Properties properties = new Properties();
            properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
            properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
            properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"127.0.0.1:9092");

            //Create producer and producer record
            Producer<String,String> producer = new KafkaProducer<>(properties);
            ProducerRecord<String,String> producerRecord = new ProducerRecord<>("my_topic","My message for Producer CallBack demo");

            //Send the data
            producer.send(producerRecord, (recordMetadata, e) -> {
                if (e == null) {
                    logger.info("Message was processed successfully.  Details are mentioned below \n");
                    logger.info("Topic::"+recordMetadata.topic()+"\n");
                    logger.info("Partition::"+recordMetadata.partition()+"\n");
                    logger.info("Offset::"+recordMetadata.offset()+"\n");
                    logger.info("Date and Time::"+new Date(recordMetadata.timestamp())+"\n");
                }
                else {
                    logger.error("Error occurred while producing the message to topic. \n");
                    logger.error("Error details are "+ e);
                }
            });

            //Flush the data
            producer.flush();

            //Flush and close the producer
            producer.close();

    }
}
