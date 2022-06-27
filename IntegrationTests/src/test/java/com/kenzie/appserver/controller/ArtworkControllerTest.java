package com.kenzie.appserver.controller;

import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.ExampleCreateRequest;
import com.kenzie.appserver.service.ArtworkService;
import com.kenzie.appserver.service.model.Artwork;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@IntegrationTest
class ArtworkControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    ArtworkService artworkService;

    private final MockNeat mockNeat = MockNeat.threadLocal();

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void getById_ArtworkExists() throws Exception {
        String id = UUID.randomUUID().toString();
        String datePosted = mockNeat.strings().valStr();
        String artistName = mockNeat.strings().valStr();
        String title = mockNeat.strings().valStr();
        String dateCreated = mockNeat.strings().valStr();
        int height = mockNeat.ints().get();
        int width = mockNeat.ints().get();
        boolean isSold = true;
        boolean isForSale = false;
        Double price = mockNeat.doubles().val();


        Artwork artwork = new Artwork(id, datePosted, artistName, title, dateCreated, height, width, isSold,
                isForSale, price);
        Artwork persistedArtwork = artworkService.addNewArtwork(artwork);
        mvc.perform(get("/example/{id}", persistedArtwork.getId()) //example will need to be replaced w/endpoint-LAURIE
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id")
                        .value(is(id)))
                .andExpect(jsonPath("datePosted")
                        .value(is(datePosted)))
                .andExpect(jsonPath("artistName")
                        .value(is(artistName)))
                .andExpect(jsonPath("title")
                        .value(is(title)))
                .andExpect(jsonPath("dateCreated")
                        .value(is(dateCreated)))
                .andExpect(jsonPath("height")
                        .value(is(height)))
                .andExpect(jsonPath("width")
                        .value(is(width)))
                .andExpect(jsonPath("isSold")
                        .value(is(isSold)))
                .andExpect(jsonPath("isForSale")
                        .value(is(isForSale)))
                .andExpect(jsonPath("price")
                        .value(is(price)))
                .andExpect(status().isOk());
    }

    //THIS TEST WAS GIVEN TO US AND WILL NEED TO BE REPLACED WITH OUR ARTWORK INSTEAD OF "EXAMPLE"*** -LAURIE
    @Test
    public void createExample_CreateSuccessful() throws Exception {
        String name = mockNeat.strings().valStr();

        ExampleCreateRequest exampleCreateRequest = new ExampleCreateRequest();
        exampleCreateRequest.setName(name);

        mapper.registerModule(new JavaTimeModule());

        mvc.perform(post("/example")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(exampleCreateRequest)))
                .andExpect(jsonPath("id")
                        .exists())
                .andExpect(jsonPath("name")
                        .value(is(name)))
                .andExpect(status().isCreated());
    }
}