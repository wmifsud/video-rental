package com.video.rental.entity;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * Created by waylon on 06/04/2017.
 */
@Entity
public class Customer {

    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true, nullable = false)
    private String idCard;
    @NotNull
    @NotEmpty
    private String name;
    @NotNull
    @NotEmpty
    private String surname;
    private Long bonusPoints;
}
