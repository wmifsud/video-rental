package com.video.rental.controller;

import com.video.rental.entity.Film;
import com.video.rental.processor.VideoRentalProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by waylon on 19/04/2017.
 */
@RestController
public class VideoRentalController {

    private static final Logger LOG = LoggerFactory.getLogger(VideoRentalController.class);

    @Autowired
    private VideoRentalProcessor videoRentalProcessor;

    @RequestMapping(value = "/calculateRent/{days}", method = RequestMethod.POST)
    public BigDecimal calculateRent(@PathVariable Integer days, @Validated @RequestBody Set<Film> filmSet) {
        LOG.info("Received request to calculateRent for {} days", days);
        BigDecimal rentalPrice = videoRentalProcessor.calculateRentalPrice(filmSet, days);
        LOG.debug("Successful request for calculateRent for {} days amounting to: {}", days, rentalPrice);
        return rentalPrice;
    }

    @RequestMapping(value = "/{idCard}/rentFilms/{days}", method = RequestMethod.POST)
    public BigDecimal rentFilms(@PathVariable String idCard, @PathVariable Integer days, @Validated @RequestBody Set<Film> films) {
        LOG.info("Received request to rentFilms for {} days to customer with idCard: {}", idCard, days);
        //TODO call processor
        LOG.debug("Successful request for rentFilms for {} days to customer with idCard: {}", idCard, days);
        return new BigDecimal(100);
    }

    @RequestMapping(value = "/{idCard}/returnFilms", method = RequestMethod.GET)
    public Set<Film> returnFilms(@PathVariable String idCard) {

        LOG.info("Received request to returnFilms for customer with idCard: {}", idCard);
        return new HashSet<Film>();
        //TODO call processor
    }

    @RequestMapping(value = "/retrieveAllFilms", method = RequestMethod.GET)
    public Set<Film> retrieveAllFilms() {

        LOG.info("Received request to retrieveAllFilms");
        return videoRentalProcessor.retrieveFilms(false);
    }

    @RequestMapping(value = "/retrieveAvailableFilms", method = RequestMethod.GET)
    public Set<Film> retrieveAvailableFilms() {

        LOG.info("Received request to retrieveAvailableFilms");
        return videoRentalProcessor.retrieveFilms(true);
    }

//    Renting one or several films and calculating the price. Returning films and calculating possible surcharges.
}
