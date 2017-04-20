package com.video.rental.entity;

import java.math.BigDecimal;

/**
 * Created by waylon on 06/04/2017.
 */
public enum FilmType {

    NEW(new BigDecimal(40), 2, 0),
    REGULAR(new BigDecimal(30), 1, 3),
    OLD(new BigDecimal(30), 1, 5);

    private BigDecimal price;
    private Integer bonusPoints;
    private Integer discountDays;


    FilmType(BigDecimal price, Integer bonusPoints, Integer discountDays) {

        this.price = price;
        this.bonusPoints = bonusPoints;
        this.discountDays = discountDays;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getBonusPoints()
    {
        return bonusPoints;
    }

    public Integer getDiscountDays() {
        return discountDays;
    }
}
