package com.vs.couponsspringkafka.repositories;

import com.vs.couponsspringkafka.beans.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, int id);

    Customer findCustomerByEmailAndPassword(String email, String password);

    Customer findCustomerByEmail(String email);
}
