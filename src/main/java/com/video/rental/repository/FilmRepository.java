package com.video.rental.repository;

import com.video.rental.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by waylon on 06/04/2017.
 */
public interface FilmRepository extends JpaRepository<Film, Long> {

}
