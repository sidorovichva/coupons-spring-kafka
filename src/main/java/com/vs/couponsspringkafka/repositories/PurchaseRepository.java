package com.vs.couponsspringkafka.repositories;

import com.vs.couponsspringkafka.beans.Coupon;
import com.vs.couponsspringkafka.beans.Customer;
import com.vs.couponsspringkafka.beans.Purchase;
import com.vs.couponsspringkafka.beans.PurchaseID;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, PurchaseID> {

    boolean existsByCouponAndCustomer(Coupon coupon, Customer customer);

    List<Purchase> getPurchasesByCustomer (Customer customer);
}
