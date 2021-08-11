package com.vs.couponsspringkafka.controllers;

import com.vs.couponsspringkafka.Exceptions.CouponRESTException;
import com.vs.couponsspringkafka.beans.Category;
import com.vs.couponsspringkafka.beans.Coupon;
import com.vs.couponsspringkafka.beans.Customer;
import com.vs.couponsspringkafka.controllers.controllerInterfaces.PurchaseControllerInterface;
import com.vs.couponsspringkafka.repositories.CustomerRepository;
import com.vs.couponsspringkafka.services.CustomerFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("purchases")
@RequiredArgsConstructor
public class PurchaseController implements PurchaseControllerInterface {

    private final CustomerFacade customerFacade;
    private final CustomerRepository customerRepository;

    private Customer user(String email) throws Exception {
        return customerRepository.findCustomerByEmail(email);
    }

    @Override
    @PostMapping
    public ResponseEntity<?> addPurchase(Principal principal, @RequestBody int couponId) throws Exception{
        Coupon coupon = customerFacade.getOneCoupon(couponId);
        customerFacade.purchaseCoupon(user(principal.getName()), coupon);
        return new ResponseEntity<>(CouponRESTException.PURCHASE_ADD.getSuccess(), HttpStatus.OK);
    }

    @Override
    @GetMapping()
    public ResponseEntity<?> getCustomerCoupons(Principal principal) throws Exception {
        return ResponseEntity.ok().body(customerFacade.getCustomerCoupons(user(principal.getName())));
    }

    @GetMapping("/not")
    public ResponseEntity<?> getNotCustomerCoupons(Principal principal) throws Exception {
        return ResponseEntity.ok().body(customerFacade.getNotCustomerCoupons(user(principal.getName())));
    }

    @Override
    @GetMapping("/{category}")
    public ResponseEntity<?> getCustomerCoupons(Principal principal, @PathVariable Category category) throws Exception {
        return ResponseEntity.ok().body(customerFacade.getCustomerCoupons(user(principal.getName()), category));
    }

    @Override
    @GetMapping("/{maxPrice}")
    public ResponseEntity<?> getCustomerCoupons(Principal principal, @PathVariable double maxPrice) throws Exception {
        return ResponseEntity.ok().body(customerFacade.getCustomerCoupons(user(principal.getName()), maxPrice));
    }

    @Override
    @GetMapping("/details")
    public ResponseEntity<?> getCustomerDetails(Principal principal) throws Exception {
        return ResponseEntity.ok().body(customerFacade.getCustomerDetails(user(principal.getName())));
    }
}
