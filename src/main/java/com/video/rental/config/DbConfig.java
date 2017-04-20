package com.video.rental.config;

import com.video.rental.entity.Customer;
import com.video.rental.entity.Film;
import com.video.rental.entity.FilmType;
import com.video.rental.repository.CustomerRepository;
import com.video.rental.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by waylon on 06/04/2017.
 *
 * Class required to pre-populate database on application
 * startup since database being used is an in-memory one.
 */
@Component
public class DbConfig implements ApplicationRunner {

    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private CustomerRepository customerRepository;

    public void run(ApplicationArguments args) throws Exception {

        populateCustomers();
        populateFilms();
    }

    public void populateCustomers() {

        Customer customer = new Customer();
        customer.setIdCard("123456M");
        customer.setName("Joe");
        customer.setSurname("Borg");
        customerRepository.save(customer);

        customer = new Customer();
        customer.setIdCard("654321M");
        customer.setName("Mary");
        customer.setSurname("Abela");
        customerRepository.save(customer);
    }

    public void populateFilms() {

        Film film = new Film();
        film.setName("Dukes of Hazzard");
        film.setFilmType(FilmType.OLD);
        filmRepository.save(film);

        film = new Film();
        film.setName("The Fate of the Furious");
        film.setFilmType(FilmType.NEW);
        filmRepository.save(film);

        film = new Film();
        film.setName("The Avengers");
        film.setFilmType(FilmType.REGULAR);
        filmRepository.save(film);

        film = new Film();
        film.setName("Batman");
        film.setFilmType(FilmType.REGULAR);
        film.setDaysRentedFor(2);
        film.setCustomer(customerRepository.findByIdCard("654321M"));
        film.setRentedOn(LocalDateTime.now().minusDays(5));
        filmRepository.save(film);
    }
}
