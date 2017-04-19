package com.video.rental.config;

import com.video.rental.entity.Film;
import com.video.rental.entity.FilmType;
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

    public void run(ApplicationArguments args) throws Exception {

        Film film = new Film();
        film.setName("Dukes of Hazzard");
        film.setFilmType(FilmType.REGULAR);
        filmRepository.save(film);

        film = new Film();
        film.setName("The Fate of the Furious");
        film.setFilmType(FilmType.NEW);
        filmRepository.save(film);
    }
}
