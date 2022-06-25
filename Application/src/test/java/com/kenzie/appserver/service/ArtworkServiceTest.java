package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.ArtworkRepository;
import com.kenzie.appserver.repositories.model.ArtworkRecord;
import com.kenzie.appserver.service.model.Artwork;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ArtworkServiceTest {
    private ArtworkRepository artworkRepository;
    private ArtworkService artworkService;

    @BeforeEach
    void setup() {
        artworkRepository = mock(ArtworkRepository.class);
        artworkService = new ArtworkService(artworkRepository);
    }
    /** ------------------------------------------------------------------------
     *  exampleService.findById
     *  ------------------------------------------------------------------------ **/

    @Test
    void findById() {
        // GIVEN
        String id = randomUUID().toString();

        ArtworkRecord record = new ArtworkRecord();
        record.setId(id);
        record.setDatePosted("testPostDate");
        record.setArtistName("testName");
        record.setTitle("testTitle");
        record.setDateCreated("testCreatedDate");
        record.setHeight(48);
        record.setWidth(48);
        record.setSold(false);
        record.setForSale(false);
        record.setPrice(50.00);

        // WHEN
        when(artworkRepository.findById(id)).thenReturn(Optional.of(record));
        Artwork artwork = artworkService.findById(id);

        // THEN
        Assertions.assertNotNull(artwork, "The object is returned");
        Assertions.assertEquals(record.getId(), artwork.getId(), "The id matches");
        Assertions.assertEquals(record.getDatePosted(), artwork.getDatePosted(), "The posted date matches");
        Assertions.assertEquals(record.getArtistName(), artwork.getArtistName(), "The artist name matches");
        Assertions.assertEquals(record.getTitle(), artwork.getTitle(), "The title matches");
        Assertions.assertEquals(record.getDateCreated(), artwork.getDateCreated(), "The created date matches");
        Assertions.assertEquals(record.getHeight(), artwork.getHeight(), "The height matches");
        Assertions.assertEquals(record.getWidth(), artwork.getWidth(), "The width matches");
        Assertions.assertEquals(record.getIsSold(), artwork.getIsSold(), "Whether the artworks are sold or " +
                "not match");
        Assertions.assertEquals(record.getIsForSale(), artwork.getIsForSale(), "Whether the artworks are for " +
                "sale or not match");
        Assertions.assertEquals(record.getPrice(), artwork.getPrice(), "The price matches");
    }

    @Test
    void findByConcertId_invalid() {
        // GIVEN
        String id = randomUUID().toString();

        when(artworkRepository.findById(id)).thenReturn(Optional.empty());

        // WHEN
        Artwork artwork = artworkService.findById(id);

        // THEN
        Assertions.assertNull(artwork, "The example is null when not found");
    }

}
