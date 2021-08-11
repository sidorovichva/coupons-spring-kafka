package com.vs.couponsspringkafka.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

public class JsonSerializerClass implements Serializer {
    private final ObjectMapper objectMapper = new ObjectMapper();
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    public JsonSerializerClass() {
    }

    @Override
    public void configure(Map configs, boolean isKey) {
    }

    @Override
    public byte[] serialize(String s, Object object) {
        if (object == null) {
            return null;
        }
        try {
            objectMapper.setDateFormat(df);
            return objectMapper.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Error serializing JSON message", e);
        }
    }

    @Override
    public void close() {
    }
}
