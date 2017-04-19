package com.video.rental.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by waylon on 06/04/2017.
 */
@Entity
public class Rent {

    @Id
    @GeneratedValue
    private Long id;
    private Date rentedOn;
    private Long daysRentedFor;
    private BigDecimal charge;
    private BigDecimal lateCharge;
}
