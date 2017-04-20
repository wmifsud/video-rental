package com.video.rental.processor;

import com.video.rental.entity.Film;
import com.video.rental.entity.FilmType;
import com.video.rental.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by waylon on 19/04/2017.
 */
@Component
public class VideoRentalProcessor {

    @Autowired
    private FilmRepository filmRepository;

    public Set<Film> retrieveFilms(boolean availableOnly) {

        List<Film> filmList;
        if (availableOnly) {

            filmList = filmRepository.findByAvailableForRentTrue();
        }
        else {

            filmList = filmRepository.findAll();
        }

        return new HashSet<Film>(filmList);
    }

    public BigDecimal calculateRentalPrice(Set<Film> filmSet, Integer daysForRent) {

        BigDecimal rentalPrice = BigDecimal.ZERO;
        for (Film film : filmSet) {

            rentalPrice = rentalPrice.add(
                    calculatePriceByFilmType(film.getFilmType(), daysForRent));
        }

        return rentalPrice;
    }

    private BigDecimal calculatePriceByFilmType(FilmType filmType, Integer daysForRent) {

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
}
