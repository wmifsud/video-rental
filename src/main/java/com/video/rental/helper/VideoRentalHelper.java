package com.video.rental.helper;

import com.video.rental.entity.Customer;
import com.video.rental.entity.Film;
import com.video.rental.entity.FilmType;
import com.video.rental.repository.CustomerRepository;
import com.video.rental.repository.FilmRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * @author waylon on 20/04/2017.
 */
@Component
public class VideoRentalHelper {

    private static final Logger LOG = LoggerFactory.getLogger(VideoRentalHelper.class);

    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private CustomerRepository customerRepository;

    public BigDecimal calculateChargeIfAny(Film film) {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime rentedTill = film.getRentedOn().plusDays(film.getDaysRentedFor());

        Long diffDays = ChronoUnit.DAYS.between(rentedTill, now);

        if (diffDays > 0) {

            LOG.debug("Returning late rental charge");
            return film.getFilmType().getPrice().multiply(BigDecimal.valueOf(diffDays));
        }
        else {
            LOG.debug("Returning no late rental charge");
            return BigDecimal.ZERO;
        }

    }

    public BigDecimal calculatePriceByFilmType(FilmType filmType, Integer daysForRent) {

        // discountPrice to be added to FilmType having discountDays > 0.
        BigDecimal discountPrice = filmType.getPrice();
        Integer offset = daysForRent - filmType.getDiscountDays();

        if (offset > 0) {

            if (filmType.getDiscountDays() > 0) {

                return discountPrice.add(
                        filmType.getPrice().multiply(
                                BigDecimal.valueOf(offset)));
            }
            return filmType.getPrice().multiply(
                    BigDecimal.valueOf(offset));
        }
        else {
            return filmType.getPrice();
        }
    }

    public Customer retrieveCustomerByIdCard(String idCard) {

        Customer customer = customerRepository.findByIdCard(idCard);

        if (customer == null) {

            throw new RuntimeException("Customer not found: " + idCard);
        }

        return customer;
    }

    public Film retrieveFilmByName(String name) {

        Film film = filmRepository.findByName(name);

        if (film == null) {

            throw new RuntimeException("Film not found: " + name);
        }

        return film;
    }

    public void validateFilmOnReturn(Film film, String idCard) {

        LOG.debug("Validating film: {} on return for idCard: {}", film.getName(), idCard);
        if (film.availableForRent()) {

            throw new RuntimeException("Film is not marked as rented.");
        }

        if (!film.getCustomer().getIdCard().equalsIgnoreCase(idCard)) {

            throw new RuntimeException("Film is rented but not under customer with idCard: " + idCard);
        }
    }

    public void validateFilmOnRental(Film film, String idCard) {

        LOG.debug("Validating film: {} on rental for idCard: {}", film.getName(), idCard);
        if (!film.availableForRent()) {

            throw new RuntimeException("Film already rented. Cannot rent it to: " + idCard);
        }
    }
}
