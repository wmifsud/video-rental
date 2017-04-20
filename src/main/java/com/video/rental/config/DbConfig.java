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

/**
 * Created by waylon on 06/04/2017.
 */
@Component
public class DbConfig implements ApplicationRunner {

    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private CustomerRepository customerRepository;

    public void run(ApplicationArguments args) throws Exception {

        populateFilms();
        populateCustomers();
    }

    private void populateCustomers() {

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

    private void populateFilms() {

        Film film = new Film();
        film.setName("Dukes of Hazzard");
        film.setAvailableForRent(true);
        film.setFilmType(FilmType.OLD);
        filmRepository.save(film);

        film = new Film();
        film.setName("The Fate of the Furious");
        film.setAvailableForRent(true);
        film.setFilmType(FilmType.NEW);
        filmRepository.save(film);

        film = new Film();
        film.setName("The Avengers");
        film.setAvailableForRent(true);
        film.setFilmType(FilmType.REGULAR);
        filmRepository.save(film);
    }
}
