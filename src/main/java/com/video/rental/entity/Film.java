package com.video.rental.entity;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Created by waylon on 06/04/2017.
 */
@Entity
public class Film {

    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    @NotEmpty
    private String name;
    private FilmType filmType;
    private LocalDateTime rentedOn;
    private Integer daysRentedFor;

    @ManyToOne
    private Customer customer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FilmType getFilmType() {
        return filmType;
    }

    public void setFilmType(FilmType filmType) {
        this.filmType = filmType;
    }

    public LocalDateTime getRentedOn()
    {
        return rentedOn;
    }

    public void setRentedOn(LocalDateTime rentedOn)
    {
        this.rentedOn = rentedOn;
    }

    public Integer getDaysRentedFor()
    {
        return daysRentedFor;
    }

    public void setDaysRentedFor(Integer daysRentedFor)
    {
        this.daysRentedFor = daysRentedFor;
    }

    public Customer getCustomer()
    {
        return customer;
    }

    public void setCustomer(Customer customer)
    {
        this.customer = customer;
    }

    @Transient
    public boolean availableForRent() {

        return rentedOn == null;
    }
}
