package com.kenzie.appserver.controller;

import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.ArtworkCreateRequest;
import com.kenzie.appserver.controller.model.ArtworkUpdateRequest;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
        //GIVEN
        //This example exists on our table*
        String id = "84cdd9ea-de0f-4841-8645-58620adf49b2";
        String datePosted = "7-06-2022";
        String artistName = "TestName";
        String title = "TestTitle";
        String dateCreated = "07-06-2022";
        int height = 10;
        int width = 20;
        boolean isSold = false;
        boolean isForSale = true;
        int price = 30;

        Artwork artwork = new Artwork(id, datePosted, artistName, title, dateCreated, height, width, isSold,
                isForSale, price);
        Artwork persistedArtwork = artworkService.addNewArtwork(artwork);

        //WHEN
        mvc.perform(get("/artwork/{id}", persistedArtwork.getId())
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
                        .value(is(false)))
                .andExpect(jsonPath("isForSale")
                        .value(is(isForSale)))
                .andExpect(jsonPath("price")
                        .value(is(price)))
                .andExpect(status().isOk());
    }

    @Test
    public void getArtwork_ArtworkDoesNotExist() throws Exception {
        // GIVEN
        String id = "12";
        // WHEN
        mvc.perform(get("/artwork/{id}", id)
            .accept(MediaType.APPLICATION_JSON))
        // THEN
            .andExpect(status().isNotFound());
    }

    //THIS TEST WAS GIVEN TO US AND WILL NEED TO BE REPLACED WITH OUR ARTWORK INSTEAD OF "EXAMPLE"*** -LAURIE
    @Test
    public void createArtwork_CreateSuccessful() throws Exception {
        String id = UUID.randomUUID().toString();
        String datePosted = mockNeat.strings().valStr();
        String artistName = mockNeat.strings().valStr();
        String title = mockNeat.strings().valStr();
        String dateCreated = mockNeat.strings().valStr();
        int height = mockNeat.ints().get();
        int width = mockNeat.ints().get();
        boolean isForSale = true;
        int price = mockNeat.ints().val();

        ArtworkCreateRequest artworkCreateRequest = new ArtworkCreateRequest();
        artworkCreateRequest.setArtistName(artistName);
        artworkCreateRequest.setTitle(title);
        artworkCreateRequest.setDateCreated(dateCreated);
        artworkCreateRequest.setIsForSale(isForSale);
        artworkCreateRequest.setPrice(price);

        mapper.registerModule(new JavaTimeModule());

        //WHEN
        mvc.perform(post("/artwork")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(artworkCreateRequest)))
        //THEN
                .andExpect(jsonPath("id")
                        .exists())
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
                        .value(is(false)))
                .andExpect(jsonPath("isForSale")
                        .value(is(isForSale)))
                .andExpect(jsonPath("price")
                        .value(is(price)))
                .andExpect(status().isCreated());
    }
    public void updateArtwork_PutSuccessful() throws Exception {
        // GIVEN
        String id = "84cdd9ea-de0f-4841-8645-58620adf49b2";
        String datePosted = "7-06-2022";
        String artistName = "TestName";
        String title = "TestTitle";
        String dateCreated = "07-06-2022";
        int height = 10;
        int width = 20;
        boolean isSold = false;
        boolean isForSale = true;
        int price = 30;

        Artwork artwork = new Artwork(id, datePosted, artistName, title, dateCreated, height, width, isSold,
                isForSale, price);
        Artwork persistedArtwork = artworkService.addNewArtwork(artwork);

        String newName = mockNeat.strings().valStr();
        int newPrice = 80;

        ArtworkUpdateRequest artworkUpdateRequest = new ArtworkUpdateRequest();
        artworkUpdateRequest.setId(id);
        artworkUpdateRequest.setDatePosted(datePosted);
        artworkUpdateRequest.setArtistName(newName);
        artworkUpdateRequest.setTitle(title);
        artworkUpdateRequest.setDateCreated(dateCreated);
        artworkUpdateRequest.setHeight(height);
        artworkUpdateRequest.setWidth(width);
        artworkUpdateRequest.setSold(isSold);
        artworkUpdateRequest.setForSale(isForSale);
        artworkUpdateRequest.setPrice(newPrice);


        mapper.registerModule(new JavaTimeModule());

        // WHEN
        mvc.perform(put("/artwork")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(artworkUpdateRequest)))
                // THEN
                .andExpect(jsonPath("id")
                        .exists())
                .andExpect(jsonPath("datePosted")
                        .value(is(datePosted)))
                .andExpect(jsonPath("artistName")
                        .value(is(newName)))
                .andExpect(jsonPath("title")
                        .value(is(title)))
                .andExpect(jsonPath("dateCreated")
                        .value(is(dateCreated)))
                .andExpect(jsonPath("height")
                        .value(is(height)))
                .andExpect(jsonPath("width")
                        .value(is(width)))
                .andExpect(jsonPath("isSold")
                        .value(is(false)))
                .andExpect(jsonPath("isForSale")
                        .value(is(isForSale)))
                .andExpect(jsonPath("price")
                        .value(is(newPrice)))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteArtwork_DeleteSuccessful() throws Exception {
        // GIVEN
        String id = UUID.randomUUID().toString();
        String datePosted = mockNeat.strings().valStr();
        String artistName = mockNeat.strings().valStr();
        String title = mockNeat.strings().valStr();
        String dateCreated = mockNeat.strings().valStr();
        int height = mockNeat.ints().get();
        int width = mockNeat.ints().get();
        int price = mockNeat.ints().val();

        Artwork artwork = new Artwork(id, datePosted, artistName, title, dateCreated, height, width, false,
                true, price);
        Artwork persistedArtwork = artworkService.addNewArtwork(artwork);

        // WHEN
        mvc.perform(delete("/artwork/{id}", persistedArtwork.getId())
                        .accept(MediaType.APPLICATION_JSON))
                // THEN
                .andExpect(status().isNoContent());
        assertThat(artworkService.findById(id)).isNull();
    }
}