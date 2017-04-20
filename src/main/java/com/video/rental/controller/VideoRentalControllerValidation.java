package com.video.rental.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author waylon on 20/04/2017.
 *
 *
 *Required to Catch the exceptions thrown
 *be the rest controller so that the error message can be extracted.
 */
@ControllerAdvice
public class VideoRentalControllerValidation {

    private static final Logger LOG = LoggerFactory.getLogger(VideoRentalControllerValidation.class);

    /**
     * Any exception which may be thrown by either the
     * gateway or the database will be caught by this method
     * and a proper description with the error message will be returned.
     * @param ex {@link Exception}
     * @return {@link String} the error message.
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public String processValidationError(Exception ex) {

        LOG.warn("The following exception was caught", ex);
        return ex.getClass().getName() + ": " + ex.getMessage();
    }
}
