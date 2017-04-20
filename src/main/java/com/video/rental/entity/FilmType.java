package com.video.rental.entity;

import java.math.BigDecimal;

/**
 * Created by waylon on 06/04/2017.
 */
public enum FilmType {

    NEW(new BigDecimal(40), 0),
    REGULAR(new BigDecimal(30), 3),
    OLD(new BigDecimal(30), 5);

    private BigDecimal price;
    private Integer discountDays;

    FilmType(BigDecimal price, Integer discountDays) {

        this.discountDays = discountDays;
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getDiscountDays() {
        return discountDays;
    }
}
