package com.video.rental.controller;

import com.video.rental.entity.Customer;
import com.video.rental.entity.Film;
import com.video.rental.processor.VideoRentalProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Set;

/**
 * Created by waylon on 19/04/2017.
 */
@RestController
public class VideoRentalController {

    private static final Logger LOG = LoggerFactory.getLogger(VideoRentalController.class);

    @Autowired
    private VideoRentalProcessor videoRentalProcessor;

    /**
     * Calculates rent only on provided films.
     * @param days {@link Integer} number of days the films are to be rented.
     * @param filmRequestSet {@link Set} set of films to rent.
     * @return {@link BigDecimal} showing total rental price.
     */
    @RequestMapping(value = "/calculateRent/{days}", method = RequestMethod.POST)
    public BigDecimal calculateRent(@PathVariable Integer days, @Validated @RequestBody Set<Film> filmRequestSet) {

        LOG.info("Received request to calculateRent for {} days", days);
        BigDecimal rentalPrice = videoRentalProcessor.calculateRentalPrice(filmRequestSet, days);
        LOG.debug("Successful request for calculateRent for {} days amounting to: {}", days, rentalPrice);
        return rentalPrice;
    }

    /**
     * Calculates rent and links films to customer who rented them.
     * @param idCard {@link String} idCard number of customer.
     * @param days {@link Integer} number of days the films are to be rented.
     * @param filmRequestSet {@link Set} set of films to rent.
     * @return {@link BigDecimal} showing total rental price.
     */
    @RequestMapping(value = "/{idCard}/rentFilms/{days}", method = RequestMethod.POST)
    public BigDecimal rentFilms(@PathVariable String idCard, @PathVariable Integer days, @Validated @RequestBody Set<Film> filmRequestSet) {

        LOG.info("Received request to rentFilms for {} days to customer with idCard: {}", days, idCard);
        return videoRentalProcessor.rentFilms(idCard, days, filmRequestSet);
    }

    /**
     * Calculates charge if films are returned late and unlinks films from customer.
     * @param idCard {@link String} idCard number of customer.
     * @param filmRequestSet {@link Set} set of films to rent.
     * @return {@link BigDecimal} showing total rental price.
     */
    @RequestMapping(value = "/{idCard}/returnFilms", method = RequestMethod.POST)
    public BigDecimal returnFilms(@PathVariable String idCard, @Validated @RequestBody Set<Film> filmRequestSet) {

        LOG.info("Received request to returnFilms for customer with idCard: {}", idCard);
        return videoRentalProcessor.returnFilms(idCard, filmRequestSet);
    }

    /**
     * Retrieves all films from the database.
     * @return {@link Set} all films from the database.
     */
    @RequestMapping(value = "/retrieveAllFilms", method = RequestMethod.GET)
    public Set<Film> retrieveAllFilms() {

        LOG.info("Received request to retrieveAllFilms");
        return videoRentalProcessor.retrieveFilms(false);
    }

    /**
     * Retrieves films which are not rented out.
     * @return {@link Set} films which are not rented out.
     */
    @RequestMapping(value = "/retrieveAvailableFilms", method = RequestMethod.GET)
    public Set<Film> retrieveAvailableFilms() {

        LOG.info("Received request to retrieveAvailableFilms");
        return videoRentalProcessor.retrieveFilms(true);
    }

    /**
     * Retrieves all customers from the database.
     * @return {@link Set} customers.
     */
    @RequestMapping(value = "/retrieveAllCustomers", method = RequestMethod.GET)
    public Set<Customer> retrieveAllCustomers() {

        LOG.info("Received request to retrieveAllCustomers");
        return videoRentalProcessor.retrieveAllCustomers();
    }
}
