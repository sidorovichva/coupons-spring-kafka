package com.vs.couponsspringkafka.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;
import com.vs.couponsspringkafka.Exceptions.CouponRESTException;
import com.vs.couponsspringkafka.Exceptions.CouponRESTExceptionHandler;
import com.vs.couponsspringkafka.Exceptions.ExpReason;
import com.vs.couponsspringkafka.repositories.CouponRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Coupon object
 */
@Component
@Data
@Entity
@Table(name = "COUPONS", indexes = @Index(name = "TitleIndex", columnList = "COMPANY_ID, TITLE", unique = true))
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "increment")
    @Column(nullable = false, name = "ID", updatable = false)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "COMPANY_ID", nullable = false)
    private Company company;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CATEGORY_ID", nullable = false)
    private Category category;

    @Column(nullable = false, length = 100, name = "TITLE")
    private String title;

    @Column(nullable = false, length = 100, name = "DESCRIPTION")
    private String description;

    @Column(nullable = false, name = "START_DATE")
    private Date startDate;

    @Column(nullable = false, name = "END_DATE")
    private Date endDate;

    @Column(nullable = false, name = "AMOUNT")
    private int amount;

    @Column(nullable = false, name = "PRICE")
    private double price;

    @Column(nullable = false, length = 100, name = "IMAGE")
    private String image;

    @OneToMany(mappedBy = "coupon", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true) //cascade = CascadeType.ALL
    @JsonIgnore
    private List<Purchase> purchase = new ArrayList<>();

    @Override
    public String toString() {
        return "Coupon{" +
                "id=" + id +
                ", company=" + company +
                ", category=" + category +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", amount=" + amount +
                ", price=" + price +
                ", image='" + image + '\'' +
                '}';
    }

    public Coupon ifAmountIsEnough(int minimum) throws CouponRESTExceptionHandler {
        if (this.getAmount() < minimum)
            throw new CouponRESTExceptionHandler(CouponRESTException.COUPON_ADD.getFailure(), ExpReason.WRONG_AMOUNT);
        else return this;
    }

    public Coupon ifEndDateIsInTheFuture() throws CouponRESTExceptionHandler {
        if (this.getEndDate().before(new Date(System.currentTimeMillis())))
            throw new CouponRESTExceptionHandler(CouponRESTException.COUPON_ADD.getFailure(), ExpReason.TOO_LATE);
        else return this;
    }

    public Coupon ifEndDateIsBeforeStartDate() throws CouponRESTExceptionHandler {
        if (this.getEndDate().before(this.getStartDate()))
            throw new CouponRESTExceptionHandler(CouponRESTException.COUPON_ADD.getFailure(), ExpReason.END_BEFORE_FINISH);
        else return this;
    }

    public Coupon ifPriceIsAboveMinimum(double minimum) throws CouponRESTExceptionHandler {
        if (this.getPrice() <= minimum)
            throw new CouponRESTExceptionHandler(CouponRESTException.COUPON_ADD.getFailure(), ExpReason.WRONG_PRICE);
        else return this;
    }

    public Coupon ifAlreadyExists(CouponRepository repository) throws CouponRESTExceptionHandler {
        if (repository.existsByTitleAndCompany(this.getTitle(), company))
            throw new CouponRESTExceptionHandler(CouponRESTException.COUPON_ADD.getFailure(), ExpReason.COUPON_ALREADY_EXISTS);
        else return this;
    }

    public Coupon ifExists(CouponRepository repository) throws CouponRESTExceptionHandler {
        if (!repository.existsByIdAndCompany(this.getId(), this.getCompany()))
            throw new CouponRESTExceptionHandler(CouponRESTException.COUPON_UPDATE.getFailure(), ExpReason.COUPON_NOT_AVAILABLE);
        else return this;
    }

    public Coupon ifExistsAnotherOne(CouponRepository repository, Company company) throws CouponRESTExceptionHandler {
        if (repository.existsByTitleAndCompanyAndIdNot(this.getTitle(), company, this.getId()))
            throw new CouponRESTExceptionHandler(CouponRESTException.COUPON_UPDATE.getFailure(), ExpReason.COUPON_ALREADY_EXISTS);
        else return this;
    }
}
