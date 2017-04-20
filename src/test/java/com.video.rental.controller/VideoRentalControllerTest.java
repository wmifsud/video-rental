package com.video.rental.controller;

import com.google.gson.Gson;
import com.video.rental.Main;
import com.video.rental.config.DbConfig;
import com.video.rental.entity.Film;
import com.video.rental.repository.FilmRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.HashSet;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author waylon on 20/04/2017.
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Main.class)
public class VideoRentalControllerTest {

    @Autowired
    private DbConfig dbConfig;
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    private static final String RENT_FILMS_URL = "/{idCard}/rentFilms/{days}";

    //Required to convert objects to json objects when sending rest requests.
    private static Gson gson = new Gson();

    private static String convertToJsonString(Object o) {
        return gson.toJson(o);
    }

    @Before
    public void setup() {

        dbConfig.populateCustomers();
        dbConfig.populateFilms();

        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    /**
     * Tests that a bad request is returned since film is already rented out.
     * @throws Exception {@link MockMvc} can throw exception.
     */
    @Test
    public void rentFilmAlreadyRented() throws Exception {

        Film film = filmRepository.findOne(1L);
        mockMvc.perform(post(RENT_FILMS_URL, "123456M", 10)
                                .content(convertToJsonString(new HashSet<>(Collections.singletonList(film))))
                                .contentType(MediaType.APPLICATION_JSON))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", is( new Long(180).intValue())));

        mockMvc.perform(post(RENT_FILMS_URL, "123456M", 10)
                                .content(convertToJsonString(new HashSet<>(Collections.singletonList(film))))
                                .contentType(MediaType.APPLICATION_JSON))
               .andDo(print())
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$", is( "java.lang.RuntimeException: Film already rented. Cannot rent it to: 123456M")));
    }
}
