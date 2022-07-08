package com.kenzie.appserver.controller;

import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.ArtworkCreateRequest;
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
        //GIVEN
        String id = UUID.randomUUID().toString();
        String datePosted = mockNeat.strings().valStr();
        String artistName = mockNeat.strings().valStr();
        String title = mockNeat.strings().valStr();
        String dateCreated = mockNeat.strings().valStr();
        int height = mockNeat.ints().get();
        int width = mockNeat.ints().get();
        boolean isSold = false;
        boolean isForSale = false;
        int price = mockNeat.ints().val();

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
        String id = UUID.randomUUID().toString();
        // WHEN
        mvc.perform(get("/concerts/{concertId}", id)
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
        artworkCreateRequest.setForSale(isForSale);
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
        String id = UUID.randomUUID().toString();
        String name = mockNeat.strings().valStr();
        String date = LocalDate.now().toString();
        Double ticketBasePrice = 90.0;

        Concert concert = new Concert(id, name, date, ticketBasePrice, false);
        Concert persistedConcert = concertService.addNewConcert(concert);

        String newName = mockNeat.strings().valStr();
        Double newTicketBasePrice = 100.0;

        ConcertUpdateRequest concertUpdateRequest = new ConcertUpdateRequest();
        concertUpdateRequest.setId(id);
        concertUpdateRequest.setDate(LocalDate.now());
        concertUpdateRequest.setName(newName);
        concertUpdateRequest.setTicketBasePrice(newTicketBasePrice);
        concertUpdateRequest.setReservationClosed(true);

        mapper.registerModule(new JavaTimeModule());

        // WHEN
        mvc.perform(put("/concerts")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(concertUpdateRequest)))
                // THEN
                .andExpect(jsonPath("id")
                        .exists())
                .andExpect(jsonPath("name")
                        .value(is(newName)))
                .andExpect(jsonPath("date")
                        .value(is(date)))
                .andExpect(jsonPath("ticketBasePrice")
                        .value(is(newTicketBasePrice)))
                .andExpect(jsonPath("reservationClosed")
                        .value(is(true)))
                .andExpect(status().isOk());
    }
}