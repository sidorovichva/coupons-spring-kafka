package com.vs.couponsspringkafka.controllers;

import com.vs.couponsspringkafka.repositories.CompanyRepository;
import com.vs.couponsspringkafka.repositories.CouponRepository;
import com.vs.couponsspringkafka.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class IndexController {

    private final CouponRepository couponRepository;
    private final CustomerRepository customerRepository;
    private final CompanyRepository companyRepository;

    @GetMapping()
    public ResponseEntity<?> getCompanyCoupons() throws Exception {
        return ResponseEntity.ok().body(couponRepository.findAll());
    }

    @GetMapping("/user/customer")
    public ResponseEntity<?> getCustomerUser(Principal principal) throws Exception {
        return ResponseEntity.ok().body(customerRepository.findCustomerByEmail(principal.getName()));
    }

    @GetMapping("/user/company")
    public ResponseEntity<?> getCompanyUser(Principal principal) throws Exception {
        System.err.println(companyRepository.findCompanyByEmail(principal.getName()));
        return ResponseEntity.ok().body(companyRepository.findCompanyByEmail(principal.getName()));
    }
}
