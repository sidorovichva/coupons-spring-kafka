package com.vs.couponsspringkafka.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vs.couponsspringkafka.AOP.SpecifyException;
import com.vs.couponsspringkafka.AOP.ValidEntry;
import com.vs.couponsspringkafka.Exceptions.CouponRESTException;
import com.vs.couponsspringkafka.Exceptions.CouponRESTExceptionHandler;
import com.vs.couponsspringkafka.Exceptions.ExpReason;
import com.vs.couponsspringkafka.beans.*;
import com.vs.couponsspringkafka.kafka.AppConfigs;
import com.vs.couponsspringkafka.kafka.PurchaseProducer;
import com.vs.couponsspringkafka.kafka.serde.JsonSerializer;
import com.vs.couponsspringkafka.repositories.CategoryRepository;
import com.vs.couponsspringkafka.repositories.CouponRepository;
import com.vs.couponsspringkafka.repositories.CustomerRepository;
import com.vs.couponsspringkafka.repositories.PurchaseRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
//@Scope("prototype")
@RequiredArgsConstructor
@Data
@Lazy
public class CustomerFacade extends Facade implements JsonSerializer {

    private final CouponRepository couponRepository;
    private final CategoryRepository categoryRepository;
    private final PurchaseRepository purchaseRepository;
    private final CustomerRepository customerRepository;
    private final PurchaseProducer purchaseProducer;

    @SpecifyException(exception = CouponRESTException.PURCHASE_ADD)
    public void purchaseCoupon(@ValidEntry Customer customer, Coupon coupon) throws Exception {
        Purchase purchase = new Purchase(coupon, customer);
        Coupon changeAmountCoupon = couponRepository.findById(coupon.getId()).get();
        changeAmountCoupon.setAmount(changeAmountCoupon.getAmount() - 1);
        couponRepository.save(changeAmountCoupon);
        purchaseRepository.save(purchase);

//        purchaseProducer.getProducer().send(new ProducerRecord<Integer, String>(
//                AppConfigs.topicName,
//                purchaseSerializerToString(purchase)
//        ));
        purchaseProducer.getProducer().send(new ProducerRecord<Integer, Purchase>(
                AppConfigs.topicName,
                purchase
        ));
//        purchaseProducer.getProducer().send(new ProducerRecord<Integer, String>(
//                AppConfigs.topicName,
//                purchase.getCoupon().toString() + " - " + purchase.getCustomer().toString()
//        ));
    }

    @SpecifyException(exception = CouponRESTException.COUPON_GET)
    public Coupon getOneCoupon(int id) throws Exception {
        Optional<Coupon> coupon = couponRepository.findById(id);
        //if (coupon == null) throw new CouponRESTExceptionHandler(CouponRESTException.COUPON_GET.getFailure(), ExpReason.COUPON_DOESNT_EXIST);
        if (coupon.isEmpty()) throw new CouponRESTExceptionHandler(CouponRESTException.COUPON_GET.getFailure(), ExpReason.COUPON_DOESNT_EXIST);
        return coupon.get();
    }

    @SpecifyException(exception = CouponRESTException.COUPON_GET)
    public List<Coupon> getAllCoupons() throws Exception{
        return couponRepository.findAll();
    }

    @SpecifyException(exception = CouponRESTException.COUPON_GET)
    public List<Coupon> getCustomerCoupons(Customer customer) throws Exception {
        return couponRepository.getCustomerCoupons(customer.getId());
    }

    @SpecifyException(exception = CouponRESTException.COUPON_GET)
    public List<Coupon> getNotCustomerCoupons(Customer customer) throws Exception {
        return couponRepository.getNotCustomerCoupons(customer.getId());
    }

    @SpecifyException(exception = CouponRESTException.COUPON_GET)
    public List<Coupon> getCustomerCoupons(Customer customer, Category category) throws Exception {
        return couponRepository.getCustomerCoupons(category, customer.getId());
    }

    @SpecifyException(exception = CouponRESTException.COUPON_GET)
    public List<Coupon> getCustomerCoupons(Customer customer, double maxPrice) throws Exception{
        return couponRepository.getCustomerCoupons(maxPrice, customer.getId());
    }

    @SpecifyException(exception = CouponRESTException.CUSTOMER_GET)
    public Customer getCustomerDetails(Customer customer) throws Exception {
        Customer details = customerRepository.findById(customer.getId()).get();
        details.setPurchases(purchaseRepository.getPurchasesByCustomer(customer));
        return details;
    }

    @SpecifyException(exception = CouponRESTException.CATEGORY_GET)
    public List<Category> getAllCategories() throws Exception {
        return categoryRepository.findAll();
    }

    @SpecifyException(exception = CouponRESTException.CATEGORY_GET)
    public Category getOneCategory(int id) throws Exception{
        return categoryRepository.findById(id).get();
    }
}
