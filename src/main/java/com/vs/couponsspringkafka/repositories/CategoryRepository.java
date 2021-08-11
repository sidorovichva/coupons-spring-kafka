package com.vs.couponsspringkafka.repositories;

import com.vs.couponsspringkafka.beans.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    boolean existsByName(String name);
}
