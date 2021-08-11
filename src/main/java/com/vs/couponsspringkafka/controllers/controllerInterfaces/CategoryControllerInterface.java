package com.vs.couponsspringkafka.controllers.controllerInterfaces;

import com.vs.couponsspringkafka.beans.Category;
import org.springframework.http.ResponseEntity;

public interface CategoryControllerInterface {
    ResponseEntity<?> addCategory(Category category) throws Exception;
    ResponseEntity<?> getAllCategories() throws Exception;
}
