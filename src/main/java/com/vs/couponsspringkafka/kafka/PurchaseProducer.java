package com.vs.couponsspringkafka.kafka;

import com.vs.couponsspringkafka.beans.Purchase;
import com.vs.couponsspringkafka.kafka.serde.JsonSerializer;
import lombok.Data;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Data
@Component
public class PurchaseProducer implements JsonSerializer{

//    private KafkaProducer<Integer, String> producer;
//
//    public PurchaseProducer() {
//        Properties properties = new Properties();
//        properties.put(ProducerConfig.CLIENT_ID_CONFIG, AppConfigs.applicationID);
//        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, AppConfigs.bootstrapServers);
//        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
//        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
//        this.producer = new KafkaProducer<Integer, String>(properties);
//    }

    private KafkaProducer<Integer, Purchase> producer;

    public PurchaseProducer() {
        Properties properties = new Properties();
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, AppConfigs.applicationID);
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, AppConfigs.bootstrapServers);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializerClass.class.getName());
        this.producer = new KafkaProducer<Integer, Purchase>(properties);
    }
}
