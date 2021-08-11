package com.vs.couponsspringkafka.commandLineRunners;

import com.vs.couponsspringkafka.Exceptions.CouponRESTExceptionHandler;
import com.vs.couponsspringkafka.beans.Category;
import com.vs.couponsspringkafka.beans.Company;
import com.vs.couponsspringkafka.beans.Coupon;
import com.vs.couponsspringkafka.beans.Customer;
import com.vs.couponsspringkafka.services.AdminFacade;
import com.vs.couponsspringkafka.services.CompanyFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
@Order(1)
@RequiredArgsConstructor
public class Population implements CommandLineRunner {

    private final AdminFacade adminFacade;
    private final CompanyFacade companyFacade;

    @Override
    public void run(String... args) throws Exception {
        companyTest();
        customerTest();
        categoryTest();
        couponTestOne(adminFacade.getOneCompany(1));
    }

    private void companyTest() throws Exception {
        try {adminFacade.addCompany(Company.builder().name("TestCompany").email("company@com.com").password("com").build());}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}
    }

    private void customerTest() throws Exception {
        try {adminFacade.addCustomer(Customer.builder().firstName("Test").lastName("Customer").email("customer@cus.com").password("cus").build());}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}
    }

    private void categoryTest() throws Exception {
        try {adminFacade.addCategory(Category.builder().id(0).name("FOOD").build());}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}
        try {adminFacade.addCategory(Category.builder().id(0).name("TRAVEL").build());}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}
        try {adminFacade.addCategory(Category.builder().id(0).name("EDUCATION").build());}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}
    }

    private void couponTestOne(Company company) throws Exception {
        try {companyFacade.addCoupon(company, Coupon.builder()
                .company(company)
                .category(companyFacade.getAllCategories().get(0))
                .title("TestCoupon")
                .description("you can't delete this coupon")
                .startDate(Date.valueOf("2021-09-02"))
                .endDate(Date.valueOf("2021-09-06"))
                .amount(500)
                .price(23.99)
                .image("AKJHDGDHJDJD").build());}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}
    }
}
