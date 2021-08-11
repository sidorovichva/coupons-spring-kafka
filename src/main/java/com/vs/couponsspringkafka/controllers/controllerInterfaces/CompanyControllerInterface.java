package com.vs.couponsspringkafka.controllers.controllerInterfaces;

import com.vs.couponsspringkafka.beans.Company;
import org.springframework.http.ResponseEntity;

public interface CompanyControllerInterface {
    ResponseEntity<?> addCompany(Company company) throws Exception;
    ResponseEntity<?> updateCompany(Company company) throws Exception;
    ResponseEntity<?> deleteCompany(int companyID) throws Exception;
    ResponseEntity<?> getAllCompanies() throws Exception;
    ResponseEntity<?> getOneCompany(int id) throws Exception;
}
