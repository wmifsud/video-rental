package com.video.rental.processor;

import com.video.rental.entity.Customer;
import com.video.rental.entity.Film;
import com.video.rental.helper.VideoRentalHelper;
import com.video.rental.repository.CustomerRepository;
import com.video.rental.repository.FilmRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by waylon on 19/04/2017.
 */
@Component
public class VideoRentalProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(VideoRentalProcessor.class);

    @Autowired
    private VideoRentalHelper videoRentalHelper;
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Method returns either all films or available films according to the parameter provided.
     * @param availableOnly {@link Boolean} showing if either all films are to e returned or not.
     * @return {@link Set<Film>}
     */
    public Set<Film> retrieveFilms(boolean availableOnly) {

        if (availableOnly) {

            return filmRepository.findByRentedOnNull();
        }
        else {

            return new HashSet<>(filmRepository.findAll());
        }
    }

    /**
     * Retrieves all customers in the system.
     * @return {@link Set<Customer>}
     */
    public Set<Customer> retrieveAllCustomers() {

        return new HashSet<>(customerRepository.findAll());
    }

    /**
     * Method rents films and links them to the respective customer.
     * @param idCard {@link String} of the customer.
     * @param days {@link Integer} number of days to be rented for.
     * @param filmRequestSet {@link Set<Film>} to be rented.
     * @return {@link BigDecimal} the total rental price.
     */
    public BigDecimal rentFilms(String idCard, Integer days, Set<Film> filmRequestSet) {

        BigDecimal rentalPrice = BigDecimal.ZERO;
        LOG.debug("Retrieving customer with idCard: {}", idCard);
        Customer customer = videoRentalHelper.retrieveCustomerByIdCard(idCard);

        for (Film filmRequest : filmRequestSet) {

            LOG.debug("Retrieving film with name: {}", filmRequest.getName());
            Film film = videoRentalHelper.retrieveFilmByName(filmRequest.getName());

            videoRentalHelper.validateFilmOnRental(film, idCard);

            rentalPrice = rentalPrice.add(videoRentalHelper.calculatePriceByFilmType(film.getFilmType(), days));
            LOG.debug("Rental price updated to: {}", rentalPrice);

            film.setCustomer(customer);
            film.setDaysRentedFor(days);
            film.setRentedOn(LocalDateTime.now());
            filmRepository.save(film);
            customer.setBonusPoints(customer.getBonusPoints() + film.getFilmType().getBonusPoints());
        }

        customerRepository.save(customer);

        LOG.debug("Returning full rental price of: {}", rentalPrice);
        return rentalPrice;
    }

    /**
     * Method returns films, unlinks films returned from respective customer
     * and checks if any charge is to be incurred to customer due to late returns.
     * @param idCard {@link String} of the customer.
     * @param filmRequestSet {@link Set<Film>} to be returned.
     * @return {@link BigDecimal} the total charge for late returns if any.
     */
    public BigDecimal returnFilms(String idCard, Set<Film> filmRequestSet) {

        BigDecimal pendingCharge = BigDecimal.ZERO;

        for (Film filmRequest : filmRequestSet) {

            LOG.debug("Retrieving film with name: {}", filmRequest.getName());
            Film film = videoRentalHelper.retrieveFilmByName(filmRequest.getName());

            videoRentalHelper.validateFilmOnReturn(film, idCard);

            pendingCharge = pendingCharge.add(videoRentalHelper.calculateChargeIfAny(film));

            film.setRentedOn(null);
            film.setDaysRentedFor(null);
            film.setCustomer(null);
            filmRepository.save(film);
        }

        LOG.debug("Returning late rental charge of: {}", pendingCharge);
        return pendingCharge;
    }

    /**
     * Calculates rental price for a set of films.
     * @param filmSet {@link Set<Film>} films to calculate rent on.
     * @param daysForRent {@link Integer} number of days to be rented for.
     * @return {@link BigDecimal} total rental calculation.
     */
    public BigDecimal calculateRentalPrice(Set<Film> filmSet, Integer daysForRent) {

        BigDecimal rentalPrice = BigDecimal.ZERO;
        for (Film film : filmSet) {

            rentalPrice = rentalPrice.add(
                    videoRentalHelper.calculatePriceByFilmType(film.getFilmType(), daysForRent));
        }

        return rentalPrice;
    }
}
