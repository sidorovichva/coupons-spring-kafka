package com.vs.couponsspringkafka.AOP;

import com.vs.couponsspringkafka.Exceptions.CouponRESTException;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SpecifyException {
    CouponRESTException exception() default CouponRESTException.COMPANY_EXISTS;
}
