package com.video.rental.controller;

import com.video.rental.entity.Film;
import com.video.rental.processor.VideoRentalProcessor;
import com.video.rental.repository.FilmRepository;
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

    @Autowired
    private FilmRepository filmRepository;

    @RequestMapping(value = "/{idCard}/rentFilms", method = RequestMethod.POST)
    public BigDecimal rentFilms(@PathVariable String idCard, @Validated @RequestBody Set<Film> films) {
        LOG.info("Received request to rentFilms for customer with idCard: {}", idCard);
        //TODO call processor
        LOG.debug("Successful request for rentFilms for customer with idCard: {}", idCard);
        return new BigDecimal(100);
    }

    @RequestMapping(value = "/{idCard}/returnFilms", method = RequestMethod.GET)
    public Set<Film> returnFilms(@PathVariable String idCard) {

        LOG.info("Received request to returnFilms for customer with idCard: {}", idCard);
        return new HashSet<Film>(filmRepository.findAll());
        //TODO call processor
    }

//    Renting one or several films and calculating the price. Returning films and calculating possible surcharges.
}
