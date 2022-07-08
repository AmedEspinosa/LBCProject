package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.ArtworkRepository;
import com.kenzie.appserver.repositories.model.ArtworkRecord;
import com.kenzie.appserver.service.model.Artwork;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;

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
     *  artworkService.findById
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
        record.setPrice(50);

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
    void findByArtworkId_invalid() {
        // GIVEN
        String id = randomUUID().toString();

        when(artworkRepository.findById(id)).thenReturn(Optional.empty());

        // WHEN
        Artwork artwork = artworkService.findById(id);

        // THEN
        Assertions.assertNull(artwork, "The example is null when not found");
    }


    @Test
    void getAllArtwork() {

         // GIVEN
        ArtworkRecord record1 = new ArtworkRecord();
        record1.setId(randomUUID().toString());
        record1.setArtistName("artistname1");
        record1.setDateCreated("recorddate1");
        record1.setDatePosted("recorddate1");
        record1.setHeight(10);
        record1.setWidth(10);
        record1.setForSale(true);
        record1.setSold(false);
        record1.setPrice(10);


        ArtworkRecord record2 = new ArtworkRecord();
        record1.setId(randomUUID().toString());
        record1.setArtistName("artistname2");
        record1.setDateCreated("recorddate2");
        record1.setDatePosted("recorddate2");
        record1.setHeight(12);
        record1.setWidth(12);
        record1.setForSale(true);
        record1.setSold(false);
        record1.setPrice(15);

        List<ArtworkRecord> recordList = new ArrayList<>();
        recordList.add(record1);
        recordList.add(record2);
        when(artworkRepository.findAll()).thenReturn(recordList);

        // WHEN
        List<Artwork> artworks = artworkService.findAllArtwork();

        // THEN
        Assertions.assertNotNull(artworks, "The artwork list is returned");
        Assertions.assertEquals(2, artworks.size(), "There are two artworks");

        for (Artwork artwork : artworks) {
            if (artwork.getId() == record1.getId()) {
                Assertions.assertEquals(record1.getId(), artwork.getId(), "The artwork id matches");
                Assertions.assertEquals(record1.getArtistName(), artwork.getArtistName(), "The artist name matches");
                Assertions.assertEquals(record1.getDateCreated(), artwork.getDateCreated(), "The date created matches");
                Assertions.assertEquals(record1.getDatePosted(), artwork.getDatePosted(), "The date posted matches");
                Assertions.assertEquals(record1.getHeight(), artwork.getHeight(), "The height matches");
                Assertions.assertEquals(record1.getWidth(), artwork.getWidth(), "The width matches");
                Assertions.assertEquals(record1.getIsForSale(), artwork.getIsForSale(), "The is for sale flag matches");
                Assertions.assertEquals(record1.getIsSold(), artwork.getIsSold(), "The is sold flag matches");
                Assertions.assertEquals(record1.getPrice(), artwork.getPrice(), "The artwork price matches");
            } else if (artwork.getId() == record2.getId()) {
                Assertions.assertEquals(record2.getId(), artwork.getId(), "The artwork id matches");
                Assertions.assertEquals(record2.getArtistName(), artwork.getArtistName(), "The artist name matches");
                Assertions.assertEquals(record2.getDateCreated(), artwork.getDateCreated(), "The date created matches");
                Assertions.assertEquals(record2.getDatePosted(), artwork.getDatePosted(), "The date posted matches");
                Assertions.assertEquals(record2.getHeight(), artwork.getHeight(), "The height matches");
                Assertions.assertEquals(record2.getWidth(), artwork.getWidth(), "The width matches");
                Assertions.assertEquals(record2.getIsForSale(), artwork.getIsForSale(), "The is for sale flag matches");
                Assertions.assertEquals(record2.getIsSold(), artwork.getIsSold(), "The is sold flag matches");
                Assertions.assertEquals(record2.getPrice(), artwork.getPrice(), "The artwork price matches");
            } else {
                Assertions.assertTrue(false, "Artwork returned that was not in the records!");
            }
        }
    }




    /** ------------------------------------------------------------------------
     *  artworkService.addNewArtwork
     *  ------------------------------------------------------------------------ **/

    /** ------------------------------------------------------------------------
     *  artworkService.updateArtwork
     *  ------------------------------------------------------------------------ **/

    /** ------------------------------------------------------------------------
     *  artworkService.deleteArtwork
     *  ------------------------------------------------------------------------ **/

    /** ------------------------------------------------------------------------
     *  artworkService.findAllArtwork
     *  ------------------------------------------------------------------------ **/

}
