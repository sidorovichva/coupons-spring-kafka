package com.vs.couponsspringkafka.kafka.serde;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vs.couponsspringkafka.beans.Coupon;
import com.vs.couponsspringkafka.beans.Purchase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public interface JsonSerializer {
    ObjectMapper objectMapper = new ObjectMapper();
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    default String purchaseSerializerToString(Purchase purchase) throws JsonProcessingException {
        //ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(df);
        return objectMapper.writeValueAsString(purchase);
    }

    default byte[] purchaseSerializerToBytes(Purchase purchase) throws JsonProcessingException {
        //ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(df);
        return objectMapper.writeValueAsBytes(purchase);
    }

}
